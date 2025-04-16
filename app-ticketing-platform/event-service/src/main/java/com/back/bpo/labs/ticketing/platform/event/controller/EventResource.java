package com.back.bpo.labs.ticketing.platform.event.controller;

import com.back.bpo.labs.ticketing.platform.event.model.Event;
import com.back.bpo.labs.ticketing.platform.event.service.IEventService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Daniel Camilo
 */
@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventResource {

    @Inject
    private IEventService eventService;

    @GET
    public List<Event> getAll() {
        return eventService.getAllEvents();
    }

    @POST
    public Boolean create(Event event) {
        return eventService.createEvent(event);
    }
}
