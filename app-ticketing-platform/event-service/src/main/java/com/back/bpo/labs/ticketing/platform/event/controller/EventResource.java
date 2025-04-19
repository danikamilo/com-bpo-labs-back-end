package com.back.bpo.labs.ticketing.platform.event.controller;

import com.back.bpo.labs.ticketing.platform.event.model.Event;
import com.back.bpo.labs.ticketing.platform.event.service.IEventService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Daniel Camilo
 */
@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventResource.class);

    @Inject
    private IEventService eventService;

    // Endpoint to get all events
    @GET
    public Response getAll() {
        try {
            List<Event> events = eventService.getAllEvents();
            return Response.ok(events).build(); // Return all events if successful
        } catch (Exception e) {
            LOGGER.error("Error while fetching the list of events", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while fetching the list of events: " + e.getMessage()) // Return error message if an exception occurs
                    .build();
        }
    }

    // Endpoint to create a new event
    @POST
    public Response create(Event event) {
        try {
            event = eventService.createEvent(event);
            if (event != null) {
                return Response.ok(event).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Event creation failed.") // Return error message if creation fails
                        .build();
            }
        } catch (Exception e) {
            LOGGER.error("Error while creating the event", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while creating the event: " + e.getMessage()) // Return error message if an exception occurs
                    .build();
        }
    }
}