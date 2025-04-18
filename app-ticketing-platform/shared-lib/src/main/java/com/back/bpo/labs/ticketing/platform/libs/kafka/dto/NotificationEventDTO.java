package com.back.bpo.labs.ticketing.platform.libs.kafka.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Date;

/**
 * @author Daniel Camilo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"eventType", "recipientEmail", "subject", "message", "referenceId", "date"})
public class NotificationEventDTO {

    @JsonProperty("eventType")
    private String eventType;

    @JsonProperty("recipientEmail")
    private String recipientEmail;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("message")
    private String message;

    @JsonProperty("referenceId")
    private String referenceId;

    @JsonProperty("date")
    private Date date;

    // Getters y setters
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
