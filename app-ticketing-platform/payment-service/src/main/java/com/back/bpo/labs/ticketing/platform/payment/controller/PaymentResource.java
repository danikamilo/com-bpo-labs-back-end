package com.back.bpo.labs.ticketing.platform.payment.controller;

import com.back.bpo.labs.ticketing.platform.payment.model.Payment;
import com.back.bpo.labs.ticketing.platform.payment.service.IPaymentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;


/**
 * @author Daniel Camilo
 */
@Path("/api/payments")
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
    public void pay(Payment payment) {
        paymentService.process(payment);
    }

    @GET
    @Path("/{id}")
    public Payment findById(@PathParam("id") String id) {
        return paymentService.findById(id);
    }
}