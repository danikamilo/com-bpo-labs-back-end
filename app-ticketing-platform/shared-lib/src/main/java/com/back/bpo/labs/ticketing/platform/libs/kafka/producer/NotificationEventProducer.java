package com.back.bpo.labs.ticketing.platform.libs.kafka.producer;

import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.NotificationEventDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class NotificationEventProducer {

    @Inject
    @Channel("notification-events-out")
    Emitter<NotificationEventDTO> notificationEmitter;

    @Inject
    @Channel("notification-payment-failed-events-out")
    Emitter<NotificationEventDTO> notificationFailedPaymentEmitter;

    public void sendNotificationPayment(NotificationEventDTO notification) {
        notificationEmitter.send(notification);
    }
    public void sendNotificationPaymentFailed(NotificationEventDTO notification) {
        notificationFailedPaymentEmitter.send(notification);
    }

}