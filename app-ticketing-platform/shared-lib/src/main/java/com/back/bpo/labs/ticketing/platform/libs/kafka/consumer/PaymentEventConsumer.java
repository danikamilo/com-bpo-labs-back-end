package com.back.bpo.labs.ticketing.platform.libs.kafka.consumer;

import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.NotificationEventDTO;
import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.PaymentEventDTO;
import com.back.bpo.labs.ticketing.platform.libs.utils.KafkaEventMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@ApplicationScoped
public class PaymentEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentEventConsumer.class);


    @Incoming("payment-events")
    @Blocking
    public void consume(String jsonEvent) {
        PaymentEventDTO event = new PaymentEventDTO();
        LOGGER.info("📩 Payment event received: ", event);
        try {
            handlePaymentPaid(event);
        } catch (Exception e) {
            LOGGER.error("❌ Error processing payment event: ", event);
        }
    }

    @Incoming("payment-failed-events")
    @Blocking
    public void consumeFiled(String jsonEvent) {
        PaymentEventDTO event = new PaymentEventDTO();
        LOGGER.info("📩 Payment failure event received: ", event);
        try {
            event = KafkaEventMapper.toObject(jsonEvent, PaymentEventDTO.class);
            handlePaymentFailed(event);
        } catch (Exception e) {
            LOGGER.error("❌ Error processing failed payment event [OrderId={}]: {}", event.getOrderId(), e.getMessage());
        }
    }

    private void handlePaymentPaid(PaymentEventDTO event) {
        LOGGER.info("✅ Handling payment success: OrderId={}", event.getOrderId());
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("payments.json"), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(event));
            writer.newLine();
            LOGGER.info("✅ Payment successfully written to file: OrderId={}", event.getOrderId());
        } catch (IOException e) {
            LOGGER.error("❌ Error writing payment event to file for OrderId={}: {}", event.getOrderId(), e.getMessage());
            LOGGER.debug("Stacktrace:", e);
        }
    }

    private void handlePaymentFailed(PaymentEventDTO event) {
        LOGGER.info("❌ Handling failed payment for OrderId={}", event.getOrderId());
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("payments-failed.json"), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(event));
            writer.newLine();
            LOGGER.info("✅ Payment failure successfully written to file: OrderId={}", event.getOrderId());
        } catch (IOException e) {
            LOGGER.error("❌ Error writing payment failure event to file for OrderId={}: {}", event.getOrderId(), e.getMessage());
            LOGGER.debug("Stacktrace:", e);
        }
    }
}