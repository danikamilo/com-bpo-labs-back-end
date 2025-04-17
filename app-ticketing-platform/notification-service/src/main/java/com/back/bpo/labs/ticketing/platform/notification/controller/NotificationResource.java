package com.back.bpo.labs.ticketing.platform.notification.controller;


import com.back.bpo.labs.ticketing.platform.notification.model.Notification;
import com.back.bpo.labs.ticketing.platform.notification.service.INotificationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


/**
 * @author Daniel Camilo
 */
@Path("/notifications")
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
    public Response notify(Notification notification) {
        return Response.status(Response.Status.CREATED).entity( service.send(notification)).build();
    }

}