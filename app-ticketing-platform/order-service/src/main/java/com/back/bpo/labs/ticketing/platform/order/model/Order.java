package com.back.bpo.labs.ticketing.platform.order.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDateTime;

/**
 * @author Daniel Camilo
 */
public class Order extends PanacheMongoEntity {

    @BsonProperty("userId")
    public String userId;

    @BsonProperty("eventId")
    public String eventId;

    @BsonProperty("quantity")
    public int quantity;

    @BsonProperty("orderDate")
    public LocalDateTime orderDate = LocalDateTime.now();

    @BsonProperty("status")
    public String status = "CREATED";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}