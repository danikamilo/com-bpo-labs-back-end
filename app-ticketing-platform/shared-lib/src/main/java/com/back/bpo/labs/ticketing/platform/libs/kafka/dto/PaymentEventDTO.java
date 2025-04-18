package com.back.bpo.labs.ticketing.platform.libs.kafka.dto;

/**
 * @author Daniel Camilo
 */
public class PaymentEventDTO {
    private String orderId;
    private String userId;
    private double amount;
    private String status;
    private String playload;

    public PaymentEventDTO() {}

    public PaymentEventDTO(String orderId, String userId, long amount, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
    }
    public String getPlayload() {
        return playload;
    }

    public void setPlayload(String playload) {
        this.playload = playload;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}