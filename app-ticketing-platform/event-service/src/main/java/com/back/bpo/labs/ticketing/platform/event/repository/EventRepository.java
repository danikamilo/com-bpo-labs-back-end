package com.back.bpo.labs.ticketing.platform.event.repository;

import com.back.bpo.labs.ticketing.platform.event.model.Event;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class EventRepository implements PanacheMongoRepository<Event> {

}

