package com.back.bpo.labs.ticketing.platform.payment.service.impl;

import com.back.bpo.labs.ticketing.platform.libs.enums.Status;
import com.back.bpo.labs.ticketing.platform.libs.exceptions.ExceptionUtil;
import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.NotificationEventDTO;
import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.PaymentEventDTO;
import com.back.bpo.labs.ticketing.platform.libs.kafka.producer.NotificationEventProducer;
import com.back.bpo.labs.ticketing.platform.libs.kafka.producer.PaymentEventProducer;
import com.back.bpo.labs.ticketing.platform.libs.utils.KafkaEventMapper;
import com.back.bpo.labs.ticketing.platform.payment.model.Payment;
import com.back.bpo.labs.ticketing.platform.payment.repository.PaymentRepository;
import com.back.bpo.labs.ticketing.platform.payment.service.IPaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class PaymentServiceImpl implements IPaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Inject
    private PaymentRepository repository;

    @Inject
    private PaymentEventProducer paymentEventProducer;

    @Inject
    private NotificationEventProducer notificationEventProducer;

    @ConfigProperty(name = "com.back.bpo.labs.ticketing.platform.payment.destination.email")
    private String destinationEmail;

    @Override
    public List<Payment> listAll() {
        try {
            List<Payment> payments = repository.listAll();
            LOGGER.info("Payments fetched successfully: {}", payments.size());
            return payments;
        } catch (Exception e) {
            LOGGER.error("Error fetching payments", e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    @Override
    public Payment process(Payment payment) throws JsonProcessingException {
        try {
            // Set status to PAID
            payment.setStatus(Status.PAID.getValue());
            repository.persist(payment);
            LOGGER.info("Payment persisted successfully: {}", payment);
            // Send payment event
            sendPaymentMessage(payment, Status.PAID.getValue());
            return payment;
        } catch (Exception e) {
            LOGGER.error("Error processing payment, sending failed event...", e);
            payment.setStatus(Status.PAYMENT_FAILED.getValue());
            // Send failed payment event
            sendPaymentFailedMessage(payment, Status.PAYMENT_FAILED.getValue());
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    @Override
    public Payment findById(String id) {
        try {
            Payment payment = repository.findById(new org.bson.types.ObjectId(id));
            LOGGER.info("Payment found: {}", payment);
            return payment;
        } catch (Exception e) {
            LOGGER.error("Error finding payment by ID: {}", id, e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }
    
    private void sendPaymentMessage(Payment payment, String statusMessage) throws JsonProcessingException {
        try {
            sendPaymentEvent(payment, statusMessage);
            sendNotificationEvent(payment, true);
            LOGGER.info("✅ Payment event sent successfully to Kafka");
        } catch (Exception e) {
            LOGGER.error("❌ Error sending payment message to Kafka", e);
        }
    }

    private void sendPaymentFailedMessage(Payment payment, String statusMessage) throws JsonProcessingException {
        try {
            sendPaymentEventFailed(payment, statusMessage);
            sendNotificationEvent(payment, false);
            LOGGER.info("✅ Payment failed event sent successfully to Kafka");
        } catch (Exception e) {
            LOGGER.error("❌ Error sending failed payment message to Kafka", e);
        }
    }

    private void sendPaymentEvent(Payment payment, String statusMessage) throws JsonProcessingException {
        PaymentEventDTO dto = buildPaymentEventDTO(payment, statusMessage);
        paymentEventProducer.sendPaymentEvent(KafkaEventMapper.toJson(dto));
    }

    private void sendPaymentEventFailed(Payment payment, String statusMessage) throws JsonProcessingException {
        PaymentEventDTO dto = buildPaymentEventDTO(payment, statusMessage);
        paymentEventProducer.sendPaymentEventFailed(KafkaEventMapper.toJson(dto));
    }

    private void sendNotificationEvent(Payment payment, boolean isSuccess) throws JsonProcessingException {
        NotificationEventDTO dto = toNotificationDTO(payment, destinationEmail);
        String json = KafkaEventMapper.toJson(dto);
        if (isSuccess) {
            notificationEventProducer.sendNotificationPayment(json);
        } else {
            notificationEventProducer.sendNotificationPaymentFailed(json);
        }
    }

    private PaymentEventDTO buildPaymentEventDTO(Payment payment, String statusMessage) throws JsonProcessingException {
        PaymentEventDTO dto = new PaymentEventDTO();
        dto.setOrderId(payment.getOrderId());
        dto.setUserId(payment.getPaymentMethod());
        dto.setAmount(payment.getAmount());
        dto.setStatus(statusMessage);
        dto.setPlayload(KafkaEventMapper.toJson(payment));
        return dto;
    }

    private NotificationEventDTO toNotificationDTO(Payment payment, String recipientEmail) {
        NotificationEventDTO dto = new NotificationEventDTO();
        setNotificationDTOFields(dto, payment, recipientEmail);
        return dto;
    }

    private void setNotificationDTOFields(NotificationEventDTO dto, Payment payment, String recipientEmail) {
        dto.setEventType("PAID".equals(payment.getStatus()) ? "PAYMENT_SUCCESS" : "PAYMENT_FAILED");
        dto.setRecipientEmail(recipientEmail);
        dto.setSubject(getSubject(payment));
        dto.setMessage(getMessage(payment));
        dto.setReferenceId(payment.getOrderId());
        dto.setDate(new Date());
    }

    private String getSubject(Payment payment) {
        return "PAID".equals(payment.getStatus()) ? "✅ Successful payment" : "❌ Payment failed";
    }

    private String getMessage(Payment payment) {
        return wrapHtml(getHeader() + getGreeting() + getBody(payment) + getPaymentInfo(payment)
                + getContactInfo() + getLegalFooter());
    }

    private String getHeader() {
        return "<h2 style='color:#ffffff;background-color:#4CAF50;padding:10px;text-align:center;'>Ticket Master</h2>";
    }

    private String getGreeting() {
        return "<p style='font-size:16px;'>Good morning dear user,</p>";
    }

    private String getBody(Payment payment) {
        if ("PAID".equals(payment.getStatus())) {
            return "<p style='font-size:16px;'>We are pleased to inform you that your payment has been successfully processed.</p>"
                    + String.format("<p>We have received your payment of <strong>%.2f</strong> for order <strong>%s</strong>. Thank you!</p>",
                    payment.getAmount(), payment.getOrderId());
        } else {
            return "<p style='font-size:16px;color:#D32F2F;'>Dear user, we regret to inform you that your payment could not be processed. Please try again or contact our support team.</p>"
                    + String.format("<p>Your payment of <strong>%.2f</strong> for order <strong>%s</strong> has failed.</p>",
                    payment.getAmount(), payment.getOrderId());
        }
    }

    private String getPaymentInfo(Payment payment) {
        return String.format("<h3>Payment Details:</h3><ul>"
                + "<li><strong>Order ID:</strong> %s</li>"
                + "<li><strong>Amount:</strong> %.2f</li>"
                + "<li><strong>Status:</strong> %s</li>"
                + "</ul>", payment.getOrderId(), payment.getAmount(), payment.getStatus());
    }

    private String getContactInfo() {
        return "<p>For more information, contact us at: "
                + "<a href='http://wa.me/573102897038'>WhatsApp</a> | "
                + "<a href='mailto:danikamilo777@gmail.com'>danikamilo777@gmail.com</a></p>";
    }

    private String getLegalFooter() {
        return "<p style='font-size:12px;color:gray;margin-top:20px;'>"
                + "The preceding email and its attachments contain confidential information... "
                + "If you are not an intended recipient, please notify the sender and delete all copies."
                + "</p>";
    }

    private String wrapHtml(String content) {
        return "<html><body style='font-family:Arial, sans-serif;'>" + content + "</body></html>";
    }

}