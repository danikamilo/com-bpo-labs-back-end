package com.back.bpo.labs.ticketing.platform.notification.model;


import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
import java.time.LocalDateTime;

/**
 * @author Daniel Camilo
 */
public class Notification extends PanacheMongoEntity {

    @BsonProperty("recipient")
    public String recipient;

    @BsonProperty("message")
    public String message;

    @BsonProperty("type")
    public String type;

    @BsonProperty("sentAt")
    public LocalDateTime sentAt = LocalDateTime.now();
}