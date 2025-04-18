package com.back.bpo.labs.ticketing.platform.libs.kafka.consumer;

import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.PaymentEventDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


@ApplicationScoped
public class PaymentEventConsumer {

    private static final Logger LOGGER = Logger.getLogger(PaymentEventConsumer.class);

    @Incoming("payment-events")
    @Blocking
    public void consume(PaymentEventDTO event) {
        LOGGER.infof("📩 Order event received: Type=%s, OrderId=%s", event.getPlayload());
        handlePaymentPaid(event);
    }

    @Incoming("payment-failed-events")
    @Blocking
    public void consumeFiled(PaymentEventDTO event) {
        LOGGER.infof("📩 Order event received: Type=%s, OrderId=%s", event.getPlayload());
        handlePaymentFailed(event);
    }

    private void handlePaymentPaid(PaymentEventDTO event) {
        LOGGER.infof("✅ Handling order creation: %s", event.getPlayload());
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("payments.json"), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(event));
            writer.newLine();
        } catch (IOException e) {
            LOGGER.errorf("Error writing order event to file: %s", e.getMessage());
        }
    }

    private void handlePaymentFailed(PaymentEventDTO event) {
        LOGGER.infof("❌ Handling failed payment for OrderId=%s", event.getOrderId());
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("payments-failed.json"), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(event));
            writer.newLine();
        } catch (IOException e) {
            LOGGER.errorf("Error writing order event to file: %s", e.getMessage());
        }
    }
}