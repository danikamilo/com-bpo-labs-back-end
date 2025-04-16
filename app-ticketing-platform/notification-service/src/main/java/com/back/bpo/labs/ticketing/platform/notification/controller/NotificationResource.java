package com.back.bpo.labs.ticketing.platform.notification.controller;


import com.back.bpo.labs.ticketing.platform.notification.model.Notification;
import com.back.bpo.labs.ticketing.platform.notification.service.INotificationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;


/**
 * @author Daniel Camilo
 */
@Path("/api/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @Inject
    private INotificationService service;

    @GET
    public List<Notification> list() {
        return service.listAll();
    }

    @POST
    public void notify(Notification notification) {
        service.send(notification);
    }
}