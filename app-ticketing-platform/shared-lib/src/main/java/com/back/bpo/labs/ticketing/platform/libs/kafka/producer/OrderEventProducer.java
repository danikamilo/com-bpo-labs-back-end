package com.back.bpo.labs.ticketing.platform.libs.kafka.producer;

import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.OrderEventDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;


/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class OrderEventProducer {

    @Inject
    @Channel("order-events-out")
    Emitter<OrderEventDTO> emitter;

    public void sendOrderEvent(OrderEventDTO event) {
        System.out.println("Hey we're inside of the request kafka-7799");
        emitter.send(event);
    }
}