package com.back.bpo.labs.ticketing.platform.gateway.controller;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
 * @author Daniel Camilo
 */
@Path("/api/gateway")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GatewayResource {

    HttpClient client = HttpClient.newHttpClient();

    @GET
    @Path("/events")
    public Response redirectToEvents() throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://event-service:5051/api/events")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return Response.ok(response.body()).build();
    }
}
