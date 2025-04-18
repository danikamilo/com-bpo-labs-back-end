package com.back.bpo.labs.ticketing.platform.libs.kafka.consumer;

import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.OrderEventDTO;
import com.back.bpo.labs.ticketing.platform.libs.utils.KafkaEventMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventConsumer.class);

    /**
     * Consumes incoming order events and processes them based on the event type.
     * Logs details of success or failure.
     */
    @Incoming("order-events-in")
    @Blocking
    public void consume(String jsonEvent) {
        LOGGER.info("📩 Order event received: "+  jsonEvent);
        OrderEventDTO event = new OrderEventDTO();
        try {
            event = KafkaEventMapper.toObject(jsonEvent, OrderEventDTO.class);
            switch (event.getEventType()) {
                case "ORDER_CREATED":
                    handleOrderCreated(event);
                    break;
                case "ORDER_CANCELLED":
                    handleOrderCancelled(event);
                    break;
                default:
                    LOGGER.warn("⚠️ Unknown event type: {}"+ event.getEventType());
            }
        } catch (Exception e) {
            LOGGER.error("❌ Error processing order event [OrderId={}, EventType={}]: {}", event.getOrderId(), event.getEventType(), e.getMessage());
        }
    }

    /**
     * Handles the creation of an order and logs success or failure.
     */
    private void handleOrderCreated(OrderEventDTO event) {
        LOGGER.info("✅ Handling order creation: "+ event.getOrderId());
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("orders.json"), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(event));
            writer.newLine();
            LOGGER.info("✅ Order creation successfully written to file: "+ event.getOrderId());
        } catch (IOException e) {
            LOGGER.error("❌ Error writing order event to file for OrderId: ", e.getMessage());
            LOGGER.debug("Stacktrace:", e);
        }
    }

    /**
     * Handles the cancellation of an order and logs the action.
     */
    private void handleOrderCancelled(OrderEventDTO event) {
        LOGGER.info("❌ Handling order cancellation: OrderId={}", event.getOrderId());
        try {
            LOGGER.info("✅ Order cancellation completed for OrderId={}", event.getOrderId());
        } catch (Exception e) {
            LOGGER.error("❌ Error handling order cancellation for OrderId={}: {}", event.getOrderId(), e.getMessage());
            LOGGER.debug("Stacktrace:", e);
        }
    }
}