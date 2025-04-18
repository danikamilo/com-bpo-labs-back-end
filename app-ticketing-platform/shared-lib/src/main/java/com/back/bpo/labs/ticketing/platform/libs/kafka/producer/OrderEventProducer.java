package com.back.bpo.labs.ticketing.platform.libs.kafka.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Producer class for sending OrderEventDTO messages to the Kafka topic.
 * Compatible with Quarkus and includes robust logging.
 *
 * @author Daniel
 */
@ApplicationScoped
public class OrderEventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventProducer.class);

    @Inject
    @Channel("order-events-out")
    Emitter<String> emitter;

    /**
     * Sends an order event to the Kafka topic and logs the result.
     *
     * @param event The order event to be sent
     */
    public void sendOrderEvent(String event) {
        try {
            emitter.send(event);
            LOGGER.info("📤 Order event sent successfully: {}", event);
        } catch (Exception e) {
            LOGGER.error("❌ Failed to send order event: {} - Error: {}", event, e.getMessage());
            LOGGER.debug("Stacktrace: ", e);
        }
    }
}