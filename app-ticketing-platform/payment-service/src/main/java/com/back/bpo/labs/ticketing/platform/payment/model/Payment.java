package com.back.bpo.labs.ticketing.platform.payment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
import java.time.LocalDateTime;
import java.util.Date;


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

    @BsonProperty("orderDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date date = new Date();

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
