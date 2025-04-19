package com.back.bpo.labs.ticketing.platform.libs.kafka.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class NotificationEventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationEventProducer.class);

    @Inject
    @Channel("notification-events-out")
    Emitter<String> notificationEmitter;

    @Inject
    @Channel("notification-payment-failed-events-out")
    Emitter<String> notificationFailedPaymentEmitter;

    /**
     * Sends a notification event to the Kafka topic for successful payments.
     *
     * @param notification The notification event data
     */
    public void sendNotificationPayment(String notification) {
        try {
            notificationEmitter.send(notification);
            LOGGER.info("📤 Notification sent successfully [Payment Success]: {}", notification);
        } catch (Exception e) {
            LOGGER.error("❌ Failed to send notification [Payment Success]: {} - Error: {}", notification, e.getMessage());
            LOGGER.debug("Stacktrace: ", e);
        }
    }

    /**
     * Sends a notification event to the Kafka topic for failed payments.
     *
     * @param notification The notification event data
     */
    public void sendNotificationPaymentFailed(String notification) {
        try {
            notificationFailedPaymentEmitter.send(notification);
            LOGGER.info("📤 Notification sent successfully [Payment Failed]: {}", notification);
        } catch (Exception e) {
            LOGGER.error("❌ Failed to send notification [Payment Failed]: {} - Error: {}", notification, e.getMessage());
            LOGGER.debug("Stacktrace: ", e);
        }
    }
}