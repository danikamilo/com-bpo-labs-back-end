package com.back.bpo.labs.ticketing.platform.libs.kafka.producer;

import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.PaymentEventDTO;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import jakarta.inject.Inject;

@ApplicationScoped
public class PaymentEventProducer {

    @Inject
    @Channel("payment-events-out")
    Emitter<PaymentEventDTO> emitter;

    @Inject
    @Channel("payment-failed-events-out")
    Emitter<PaymentEventDTO> paymentFailedEventEmitter;

    public void sendPaymentEvent(PaymentEventDTO event) {
        emitter.send(event);
    }

    public void sendPaymentEventFailed(PaymentEventDTO event) {
        paymentFailedEventEmitter.send(event);
    }


}