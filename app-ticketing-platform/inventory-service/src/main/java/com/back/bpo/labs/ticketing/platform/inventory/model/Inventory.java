package com.back.bpo.labs.ticketing.platform.inventory.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * @author Daniel Camilo
 */
public class Inventory extends PanacheMongoEntity {

    @BsonProperty("eventId")
    public String eventId;

    @BsonProperty("totalTickets")
    public int totalTickets;

    @BsonProperty("availableTickets")
    public int availableTickets;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
}