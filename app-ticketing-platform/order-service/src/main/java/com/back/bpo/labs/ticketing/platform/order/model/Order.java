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
}