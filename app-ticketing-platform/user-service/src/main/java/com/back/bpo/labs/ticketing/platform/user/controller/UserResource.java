package com.back.bpo.labs.ticketing.platform.user.controller;


import com.back.bpo.labs.ticketing.platform.user.model.User;
import com.back.bpo.labs.ticketing.platform.user.service.IUserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

/**
 * @author Daniel Camilo
 */
@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private IUserService userService;

    @GET
    public List<User> getAllUsers() {
        return userService.listAllUsers();
    }

    @POST
    public void createUser(User user) {
        userService.createUser(user);
    }

    @GET
    @Path("/{id}")
    public User getUser(@PathParam("id") String id) {
        return userService.findById(id);
    }
}