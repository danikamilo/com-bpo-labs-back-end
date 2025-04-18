package com.back.bpo.labs.ticketing.platform.inventory.controller;

import com.back.bpo.labs.ticketing.platform.inventory.model.Inventory;
import com.back.bpo.labs.ticketing.platform.inventory.service.IInventoryService;
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
@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventoryResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryResource.class);

    @Inject
    private IInventoryService service;

    // Endpoint to get all inventory items
    @GET
    public Response list() {
        try {
            List<Inventory> inventoryList = service.listAll();
            return Response.ok(inventoryList).build(); // Return the list of inventory items if successful
        } catch (Exception e) {
            LOGGER.error("Error while fetching the list of inventory", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while fetching the list of inventory: " + e.getMessage()) // Return error message if an exception occurs
                    .build();
        }
    }

    // Endpoint to get inventory by event ID
    @GET
    @Path("/{eventId}")
    public Response get(@PathParam("eventId") String eventId) {
        try {
            Inventory inventory = service.findByEvent(eventId);
            if (inventory != null) {
                return Response.ok(inventory).build(); // Return inventory if found
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Inventory not found for event: " + eventId) // Return 404 if inventory not found
                        .build();
            }
        } catch (Exception e) {
            LOGGER.error("Error while fetching inventory for event: " + eventId, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while fetching inventory: " + e.getMessage()) // Return error message if an exception occurs
                    .build();
        }
    }

    // Endpoint to add a new inventory item
    @POST
    public Response add(Inventory inventory) {
        try {
            Inventory addedInventory = service.addInventory(inventory);
            return Response.status(Response.Status.CREATED).entity(addedInventory).build(); // Return created inventory if successful
        } catch (Exception e) {
            LOGGER.error("Error while adding inventory", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while adding inventory: " + e.getMessage()) // Return error message if an exception occurs
                    .build();
        }
    }

    // Endpoint to reserve tickets for an event
    @POST
    @Path("/reserve/{eventId}/{quantity}")
    public Response reserve(@PathParam("eventId") String eventId, @PathParam("quantity") int quantity) {
        try {
            Inventory reserved = service.reserveTicket(eventId, quantity);
            if (reserved != null) {
                return Response.status(Response.Status.OK).entity("Tickets reserved successfully").build(); // Return success message if reservation is successful
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Failed to reserve tickets") // Return failure message if reservation fails
                        .build();
            }
        } catch (Exception e) {
            LOGGER.error("Error while reserving tickets for event: " + eventId, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while reserving tickets: " + e.getMessage()) // Return error message if an exception occurs
                    .build();
        }
    }
}