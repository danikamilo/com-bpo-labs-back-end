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

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class PaymentServiceImpl implements IPaymentService {

    @Inject
    private PaymentRepository repository;

    @Inject
    private PaymentEventProducer paymentEventProducer;

    @Inject
    private NotificationEventProducer notificationEventProducer;

    public List<Payment> listAll() {
        return repository.listAll();
    }

    public Payment process(Payment payment) throws JsonProcessingException {
        try {
            payment.setStatus(Status.PAID.getValue());
            repository.persist(payment);
            sendPaymentMessage(payment, Status.PAID.getValue());
            return payment;
        } catch (Exception e) {
            payment.setStatus(Status.PAYMENT_FAILED.getValue());
            sendPaymentFailedMessage(payment, Status.PAYMENT_FAILED.getValue());
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    public Payment findById(String id) {
        try {
            return repository.findById(new org.bson.types.ObjectId(id));
        } catch (Exception e) {
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    public void sendPaymentMessage(Payment payment, String message) throws JsonProcessingException {
        PaymentEventDTO dto = new PaymentEventDTO();
        dto.setOrderId(payment.getOrderId());
        dto.setUserId(payment.getPaymentMethod());
        dto.setAmount(payment.getAmount());
        dto.setStatus(message);
        dto.setPlayload(KafkaEventMapper.toJson(payment));
        paymentEventProducer.sendPaymentEvent(dto);
        notificationEventProducer.sendNotificationPayment(toNotificationDTO(payment,"email"));
    }

    public void sendPaymentFailedMessage(Payment payment, String message) throws JsonProcessingException {
        PaymentEventDTO dto = new PaymentEventDTO();
        dto.setOrderId(payment.getOrderId());
        dto.setUserId(payment.getPaymentMethod());
        dto.setAmount(payment.getAmount());
        dto.setStatus(message);
        dto.setPlayload(KafkaEventMapper.toJson(payment));
        paymentEventProducer.sendPaymentEventFailed(dto);
        notificationEventProducer.sendNotificationPaymentFailed(toNotificationDTO(payment,"email"));
    }

    public NotificationEventDTO toNotificationDTO(Payment payment, String recipientEmail) {
        String subject = getSubject(payment);
        String message = getMessage(payment);
        NotificationEventDTO dto = new NotificationEventDTO();
        setNotificationDTOFields(dto, payment, recipientEmail, subject, message);
        return dto;
    }

    private String getSubject(Payment payment) {
        return Status.PAID.getValue().equals(payment.getStatus()) ? "✅ Successful payment" : "❌ Payment failed";
    }

    private String getMessage(Payment payment) {
        return Status.PAID.getValue().equals(payment.getStatus())
                ? String.format("We have received your payment of $%d for order %s. Thank you!", payment.getAmount(), payment.getOrderId())
                : String.format("Hello, your payment of $%d for order %s has failed. Please try again or contact us.", payment.getAmount(), payment.getOrderId());
    }

    private void setNotificationDTOFields(NotificationEventDTO dto, Payment payment, String recipientEmail, String subject, String message) {
        dto.setEventType(payment.getStatus().equals("PAID") ? "PAYMENT_SUCCESS" : "PAYMENT_FAILED");
        dto.setRecipientEmail(recipientEmail);
        dto.setSubject(subject);
        dto.setMessage(message);
        dto.setReferenceId(payment.getOrderId());
        dto.setTimestamp(LocalDateTime.now());
    }
}
