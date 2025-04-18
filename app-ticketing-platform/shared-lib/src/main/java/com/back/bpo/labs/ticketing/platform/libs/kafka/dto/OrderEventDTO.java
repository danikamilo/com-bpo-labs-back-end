package com.back.bpo.labs.ticketing.platform.libs.kafka.dto;


/**
 * @author Daniel Camilo
 */
public class OrderEventDTO {
    public String orderId;
    public String eventType;
    public String payload;

    public OrderEventDTO(){

    }

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
