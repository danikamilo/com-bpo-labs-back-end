package com.back.bpo.labs.ticketing.platform.libs.kafka.consumer;

import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.OrderEventDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class OrderEventConsumer {

    private static final Logger LOGGER = Logger.getLogger(OrderEventConsumer.class);

    @Incoming("order-events-in")
    @Blocking
    public void consume(OrderEventDTO event) {
        LOGGER.infof("📩 Order event received: Type=%s, OrderId=%s", event.payload);
        switch (event.eventType) {
            case "ORDER_CREATED":
                handleOrderCreated(event);
                break;
            case "ORDER_CANCELLED":
                handleOrderCancelled(event);
                break;
            default:
                LOGGER.warnf("⚠️ Unknown event type: %s", event.eventType);
        }
    }

    private void handleOrderCreated(OrderEventDTO event) {
        LOGGER.infof("✅ Handling order creation: %s", event.payload);
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("orders.json"), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(event));
            writer.newLine();
        } catch (IOException e) {
            LOGGER.errorf("Error writing order event to file: %s", e.getMessage());
        }
    }

    private void handleOrderCancelled(OrderEventDTO event) {
        LOGGER.infof("❌ Handling order cancellation: %s", event.payload);
    }
}