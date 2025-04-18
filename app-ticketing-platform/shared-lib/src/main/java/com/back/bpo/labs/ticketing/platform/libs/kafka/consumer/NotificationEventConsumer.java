package com.back.bpo.labs.ticketing.platform.libs.kafka.consumer;

import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.NotificationEventDTO;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class NotificationEventConsumer {

    private static final Logger LOGGER = Logger.getLogger(NotificationEventConsumer.class);
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String SMTP_USER = "hardsoftware.2014@gmail.com";
    private static final String SMTP_PASS = "tu-contraseña-de-aplicación";

    // Consumidor para el canal "notification-events" (Pagos exitosos)
    @Incoming("notification-events")
    @Blocking
    public void consumeSuccessNotification(NotificationEventDTO event) {
        try {
            LOGGER.infof("📩 Notification event received: Type=%s, OrderId=%s", event.getEventType(), event.getReferenceId());
            sendSuccessNotification(event);
        } catch (Exception e) {
            LOGGER.errorf("❌ Error processing successful payment notification: %s", e.getMessage());
        }
    }

    // Consumidor para el canal "notification-payment-failed-events" (Pagos fallidos)
    @Incoming("notification-payment-failed-events")
    @Blocking
    public void consumeFailureNotification(NotificationEventDTO event) {
        LOGGER.infof("📩 Payment failed notification received: OrderId=%s", event.getReferenceId());
        sendFailureNotification(event);
    }

    // Enviar notificación de pago exitoso
    private void sendSuccessNotification(NotificationEventDTO event) {
        sendEmail(event.getRecipientEmail(), event.getSubject(), event.getMessage());
        LOGGER.infof("📧 Payment successful notification sent to %s", event.getRecipientEmail());
    }

    // Enviar notificación de pago fallido
    private void sendFailureNotification(NotificationEventDTO event) {
        sendEmail(event.getRecipientEmail(), event.getSubject(), event.getMessage());
        LOGGER.infof("📧 Payment failed notification sent to %s", event.getRecipientEmail());
    }

    private void sendEmail(String recipientEmail, String subject, String message) {
        Session session = createEmailSession();
        try {
            MimeMessage mimeMessage = buildMimeMessage(session, recipientEmail, subject, message);
            Transport.send(mimeMessage);
            LOGGER.infof("📧 Email sent to: %s", recipientEmail);
        } catch (MessagingException e) {
            LOGGER.errorf("❌ Error sending email: %s", e.getMessage());
        }
    }

    private Session createEmailSession() {
        Properties properties = getEmailProperties();
        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(SMTP_USER, SMTP_PASS);
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
            throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(SMTP_USER));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(message);
        return mimeMessage;
    }
}
