package com.back.bpo.labs.ticketing.platform.payment.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
import java.time.LocalDateTime;


/**
 * @author Daniel Camilo
 */
public class Payment extends PanacheMongoEntity {

    @BsonProperty("paymentId")
    public String paymentId;

    @BsonProperty("orderId")
    public String orderId;

    @BsonProperty("amount")
    public double amount;

    @BsonProperty("paymentMethod")
    public String paymentMethod;

    @BsonProperty("status")
    public String status = "PENDING";

    @BsonProperty("timestamp")
    public LocalDateTime timestamp = LocalDateTime.now();

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
