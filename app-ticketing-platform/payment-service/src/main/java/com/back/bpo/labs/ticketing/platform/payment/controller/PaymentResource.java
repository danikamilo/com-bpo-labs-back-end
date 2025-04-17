package com.back.bpo.labs.ticketing.platform.payment.controller;

import com.back.bpo.labs.ticketing.platform.payment.model.Payment;
import com.back.bpo.labs.ticketing.platform.payment.service.IPaymentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


/**
 * @author Daniel Camilo
 */
@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @Inject
    private IPaymentService paymentService;

    @GET
    public List<Payment> list() {
        return paymentService.listAll();
    }

    @POST
    public Response pay(Payment payment) {
        return Response.status(Response.Status.CREATED).entity(paymentService.process(payment)).build();
    }

    @GET
    @Path("/{id}")
    public Payment findById(@PathParam("id") String id) {
        return paymentService.findById(id);
    }
}