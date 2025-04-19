package com.back.bpo.labs.ticketing.platform.libs.kafka.consumer;

import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.NotificationEventDTO;
import com.back.bpo.labs.ticketing.platform.libs.utils.KafkaEventMapper;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Kafka consumer for notification events, sending email notifications for successful and failed payments.
 *
 * @author Daniel
 */
@ApplicationScoped
public class NotificationEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationEventConsumer.class);

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USER = "hardsoftware.2014@gmail.com";
    private static final String SMTP_PASS = "hnui wuny aaus bcph";

    @Incoming("notification-events")
    @Blocking
    public void consumeSuccessNotification(String jsonEvent) {
        NotificationEventDTO event = new NotificationEventDTO();
        try {
            event = KafkaEventMapper.toObject(jsonEvent, NotificationEventDTO.class);
            LOGGER.info("📩 Notification received [SUCCESS]: Type=%s | OrderId=%s", event.getEventType(), event.getReferenceId());
            sendSuccessNotification(event);
        } catch (Exception e) {
            LOGGER.error("❌ Error processing success notification for OrderId=%s: %s", event.getReferenceId(), e.getMessage());;
            LOGGER.debug("Stacktrace:", e);
        }
    }

    @Incoming("notification-payment-failed-events")
    @Blocking
    public void consumeFailureNotification(String jsonEvent) {
        NotificationEventDTO event = new NotificationEventDTO();
        try {
            event = KafkaEventMapper.toObject(jsonEvent, NotificationEventDTO.class);
            LOGGER.info("📩 Notification received: ", event);
            sendFailureNotification(event);
        } catch (Exception e) {
            LOGGER.error("❌ Error processing failed notification: ", event);
            LOGGER.debug("Stacktrace:", e);
        }
    }

    private void sendSuccessNotification(NotificationEventDTO event) {
        sendEmail(event.getRecipientEmail(), event.getSubject(), event.getMessage());
        LOGGER.info("✅ Payment success email sent to %s", event.getRecipientEmail());
    }

    private void sendFailureNotification(NotificationEventDTO event) {
        sendEmail(event.getRecipientEmail(), event.getSubject(), event.getMessage());
        LOGGER.info("✅ Payment failure email sent to %s", event.getRecipientEmail());
    }

    private void sendEmail(String recipientEmail, String subject, String message) {
        Session session = createEmailSession();
        try {
            MimeMessage mimeMessage = buildMimeMessage(session, recipientEmail, subject, message);
            Transport.send(mimeMessage);
            LOGGER.info("📧 Email successfully sent to: %s", recipientEmail);
        } catch (MessagingException | UnsupportedEncodingException e) {
            LOGGER.error("❌ Failed to send email to %s: %s", recipientEmail, e.getMessage());
            LOGGER.debug("Stacktrace:", e);
        }
    }

    private Session createEmailSession() {
        Properties properties = getEmailProperties();
        return Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASS);
            }
        });
    }

    private Properties getEmailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        return properties;
    }

    private MimeMessage buildMimeMessage(Session session, String recipient, String subject, String message)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress());
        mimeMessage.setFrom(new InternetAddress(SMTP_USER, "BPO LABS - TicketMaster"));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        mimeMessage.setSubject(subject);
        mimeMessage.setContent(message, "text/html; charset=utf-8");
        return mimeMessage;
    }
}