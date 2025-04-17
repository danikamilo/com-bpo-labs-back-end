package com.back.bpo.labs.ticketing.platform.order.controller;

import com.back.bpo.labs.ticketing.platform.order.model.Order;
import com.back.bpo.labs.ticketing.platform.order.service.IOrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


/**
 * @author Daniel Camilo
 */
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    private IOrderService service;

    @GET
    public List<Order> list() {
        return service.listAll();
    }

    @POST
    public Response create(Order order) {
        return Response.status(Response.Status.CREATED).entity(service.create(order)).build();
    }

    @GET
    @Path("/{id}")
    public Order findById(@PathParam("id") String id) {
        return service.findById(id);
    }
}
