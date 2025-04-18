package com.back.bpo.labs.ticketing.platform.notification.controller;

import com.back.bpo.labs.ticketing.platform.notification.model.Notification;
import com.back.bpo.labs.ticketing.platform.notification.service.INotificationService;
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
@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationResource.class);

    @Inject
    private INotificationService service;

    // Endpoint to list all notifications
    @GET
    public Response list() {
        try {
            List<Notification> notifications = service.listAll();
            return Response.ok(notifications).build(); // Return all notifications if successful
        } catch (Exception e) {
            LOGGER.error("Error while fetching the list of notifications", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while fetching the list of notifications: " + e.getMessage()) // Return error message if an exception occurs
                    .build();
        }
    }

    // Endpoint to send a new notification
    @POST
    public Response notify(Notification notification) {
        try {
            Notification sentNotification = service.send(notification);
            return Response.status(Response.Status.CREATED).entity(sentNotification).build(); // Return the sent notification if successful
        } catch (Exception e) {
            LOGGER.error("Error while sending the notification", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while sending the notification: " + e.getMessage()) // Return error message if sending fails
                    .build();
        }
    }
}