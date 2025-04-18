package com.back.bpo.labs.ticketing.platform.libs.kafka.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Daniel Camilo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"orderId", "eventType", "payload"})
public class OrderEventDTO {

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("eventType")
    private String eventType;

    @JsonProperty("payload")
    private String payload;

    // Constructor por defecto
    public OrderEventDTO() {}

    // Getters y setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}