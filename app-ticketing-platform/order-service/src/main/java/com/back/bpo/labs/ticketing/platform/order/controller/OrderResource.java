package com.back.bpo.labs.ticketing.platform.order.controller;

import com.back.bpo.labs.ticketing.platform.order.model.Order;
import com.back.bpo.labs.ticketing.platform.order.service.IOrderService;
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
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);

    @Inject
    private IOrderService service;

    // Endpoint to list all orders
    @GET
    public Response list() {
        try {
            List<Order> orders = service.listAll();
            return Response.ok(orders).build(); // Return all orders if successful
        } catch (Exception e) {
            LOGGER.error("Error while fetching the list of orders", e); // Usando logback para logear el error
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while fetching the list of orders: " + e.getMessage())
                    .build();
        }
    }

    // Endpoint to create a new order
    @POST
    public Response create(Order order) {
        try {
            Order createdOrder = service.create(order);
            return Response.status(Response.Status.CREATED).entity(createdOrder).build();
        } catch (Exception e) {
            LOGGER.error("Error while creating the order", e); // Usando logback para logear el error
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while creating the order: " + e.getMessage())
                    .build();
        }
    }

    // Endpoint to find an order by ID
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") String id) {
        try {
            Order order = service.findById(id);
            if (order == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Order not found for id: " + id)
                        .build();
            }
            return Response.ok(order).build();
        } catch (Exception e) {
            LOGGER.error("Error while fetching the order with id: " + id, e); // Usando logback para logear el error
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while fetching the order: " + e.getMessage())
                    .build();
        }
    }
}
