package com.back.bpo.labs.ticketing.platform.libs.kafka.producer;

import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.PaymentEventDTO;
import com.back.bpo.labs.ticketing.platform.libs.utils.KafkaEventMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Producer class for sending PaymentEventDTO messages to Kafka topics
 * for both successful and failed payment events.
 *
 * @author Daniel
 */
@ApplicationScoped
public class PaymentEventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentEventProducer.class);

    @Inject
    @Channel("payment-events-out")
    Emitter<String> paymentEventEmitter;

    @Inject
    @Channel("payment-failed-events-out")
    Emitter<String> paymentFailedEventEmitter;

    /**
     * Sends a payment event to the Kafka topic for successful payments.
     *
     * @param event The payment event to send
     */
    public void sendPaymentEvent(String event) {
        try {
            paymentEventEmitter.send(event);
            LOGGER.info("📤 Payment event sent successfully [SUCCESS]: {}", event);
        } catch (Exception e) {
            LOGGER.error("❌ Failed to send payment event [SUCCESS]: {} - Error: {}", event, e.getMessage());
            LOGGER.debug("Stacktrace: ", e);
        }
    }

    /**
     * Sends a payment event to the Kafka topic for failed payments.
     *
     * @param event The failed payment event to send
     */
    public void sendPaymentEventFailed(String event) {
        try {
            // Build Payment Event DTO
            paymentFailedEventEmitter.send(event);
            LOGGER.info("📤 Payment event sent successfully [FAILED]: {}", event);
        } catch (Exception e) {
            LOGGER.error("❌ Failed to send payment event [FAILED]: {} - Error: {}", event, e.getMessage());
            LOGGER.debug("Stacktrace: ", e);
        }
    }
}