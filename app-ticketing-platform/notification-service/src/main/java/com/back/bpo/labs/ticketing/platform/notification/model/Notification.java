package com.back.bpo.labs.ticketing.platform.notification.model;


import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;
import java.time.LocalDateTime;

/**
 * @author Daniel Camilo
 */
public class Notification extends PanacheMongoEntity {

    @BsonProperty("notificationId")
    public String notificationId;
    @BsonProperty("recipient")
    public String recipient;
    @BsonProperty("message")
    public String message;
    @BsonProperty("type")
    public String type;
    @BsonProperty("sentAt")
    public LocalDateTime sentAt = LocalDateTime.now();

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}