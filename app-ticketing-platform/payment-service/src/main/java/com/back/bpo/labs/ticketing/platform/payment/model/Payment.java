package com.back.bpo.labs.ticketing.platform.payment.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
import java.time.LocalDateTime;


/**
 * @author Daniel Camilo
 */
public class Payment extends PanacheMongoEntity {

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
}
