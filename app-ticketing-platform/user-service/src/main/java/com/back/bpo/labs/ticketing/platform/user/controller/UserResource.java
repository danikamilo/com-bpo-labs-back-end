package com.back.bpo.labs.ticketing.platform.user.controller;

import com.back.bpo.labs.ticketing.platform.user.model.User;
import com.back.bpo.labs.ticketing.platform.user.service.IUserService;
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
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private IUserService userService;

    // Endpoint to list all users
    @GET
    public Response getAllUsers() {
        try {
            List<User> users = userService.listAllUsers();
            return Response.ok(users).build(); // Return all users if successful
        } catch (Exception e) {
            LOGGER.error("Error while fetching the list of users", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while fetching the list of users: " + e.getMessage()) // Return error message if an exception occurs
                    .build();
        }
    }

    // Endpoint to create a new user
    @POST
    public Response createUser(User user) {
        try {
            User createdUser = userService.createUser(user);
            return Response.status(Response.Status.CREATED).entity(createdUser).build(); // Return the created user if successful
        } catch (Exception e) {
            LOGGER.error("Error while creating user", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while creating user: " + e.getMessage()) // Return error message if creation fails
                    .build();
        }
    }

    // Endpoint to find a user by ID
    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") String id) {
        try {
            User user = userService.findById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("User not found for id: " + id) // Return not found message if the user does not exist
                        .build();
            }
            return Response.ok(user).build(); // Return user if found
        } catch (Exception e) {
            LOGGER.error("Error while fetching the user with id: " + id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while fetching the user: " + e.getMessage()) // Return error message for any exception
                    .build();
        }
    }
}