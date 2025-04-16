package com.back.bpo.labs.ticketing.platform.order.controller;

import com.back.bpo.labs.ticketing.platform.order.model.Order;
import com.back.bpo.labs.ticketing.platform.order.service.impl.IOrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;


/**
 * @author Daniel Camilo
 */
@Path("/api/orders")
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
    public void create(Order order) {
        service.create(order);
    }

    @GET
    @Path("/{id}")
    public Order findById(@PathParam("id") String id) {
        return service.findById(id);
    }
}
