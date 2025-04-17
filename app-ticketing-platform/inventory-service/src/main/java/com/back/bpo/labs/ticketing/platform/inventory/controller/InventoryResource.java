package com.back.bpo.labs.ticketing.platform.inventory.controller;

import com.back.bpo.labs.ticketing.platform.inventory.model.Inventory;
import com.back.bpo.labs.ticketing.platform.inventory.service.IInventoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


/**
 * @author Daniel Camilo
 */
@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventoryResource {

    @Inject
    private IInventoryService service;

    @GET
    public List<Inventory> list() {
        return service.listAll();
    }

    @GET
    @Path("/{eventId}")
    public Inventory get(@PathParam("eventId") String eventId) {
        return service.findByEvent(eventId);
    }

    @POST
    public Response add(Inventory inventory) {
        return Response.status(Response.Status.CREATED).entity(service.addInventory(inventory)).build();
    }

    @POST
    @Path("/reserve/{eventId}/{quantity}")
    public Response reserve(@PathParam("eventId") String eventId, @PathParam("quantity") int quantity) {
        return Response.status(Response.Status.OK).entity(service.reserveTicket(eventId, quantity)).build();

    }
}