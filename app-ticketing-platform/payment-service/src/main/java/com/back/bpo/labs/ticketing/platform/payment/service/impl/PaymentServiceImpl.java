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
            sendPaymentMessage(payment, Status.PAYMENT_FAILED.getValue());
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
            // Build Payment Event DTO
            PaymentEventDTO paymentEventDTO = buildPaymentEventDTO(payment, statusMessage);
            String paymentMessage = KafkaEventMapper.toJson(paymentEventDTO);
            paymentEventProducer.sendPaymentEvent(paymentMessage);

            // Send Notification Message
            NotificationEventDTO notificationEventDTO = toNotificationDTO(payment, "danikamilo777@gmail.com");
            String notificationMessage = KafkaEventMapper.toJson(notificationEventDTO);
            notificationEventProducer.sendNotificationPayment(notificationMessage);

            LOGGER.info("Payment event sent successfully to Kafka");
        } catch (Exception e) {
            LOGGER.error("Error sending payment message to Kafka", e);
        }
    }

    private void sendPaymentFailedMessage(Payment payment, String statusMessage) throws JsonProcessingException {
        try {
            // Build Payment Event DTO for failed payment
            PaymentEventDTO paymentEventDTO = buildPaymentEventDTO(payment, statusMessage);
            paymentEventProducer.sendPaymentEventFailed(KafkaEventMapper.toJson(paymentEventDTO));

            // Send Notification for failed payment
            NotificationEventDTO notificationEventDTO = toNotificationDTO(payment, "danikamilo777@gmail.com");
            notificationEventProducer.sendNotificationPaymentFailed(KafkaEventMapper.toJson(notificationEventDTO));

            LOGGER.info("Payment failed event sent successfully to Kafka");
        } catch (Exception e) {
            LOGGER.error("Error sending failed payment message to Kafka", e);
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
        String subject = getSubject(payment);
        String message = getMessage(payment);
        NotificationEventDTO dto = new NotificationEventDTO();
        setNotificationDTOFields(dto, payment, recipientEmail, subject, message);
        return dto;
    }

    private String getSubject(Payment payment) {
        return Status.PAID.getValue().equals(payment.getStatus()) ?
                "✅ Successful payment" : "❌ Payment failed";
    }

    private String getMessage(Payment payment) {
        return Status.PAID.getValue().equals(payment.getStatus()) ?
                String.format("We have received your payment of %.2f for order %s. Thank you!",
                        payment.getAmount(), payment.getOrderId()) :
                String.format("Hello, your payment of %.2f for order %s has failed. Please try again or contact us.",
                        payment.getAmount(), payment.getOrderId());
    }

    private void setNotificationDTOFields(NotificationEventDTO dto, Payment payment, String recipientEmail, String subject, String message) {
        dto.setEventType(payment.getStatus().equals("PAID") ? "PAYMENT_SUCCESS" : "PAYMENT_FAILED");
        dto.setRecipientEmail(recipientEmail);
        dto.setSubject(subject);
        dto.setMessage(message);
        dto.setReferenceId(payment.getOrderId());
        dto.setDate(new Date());
    }
}