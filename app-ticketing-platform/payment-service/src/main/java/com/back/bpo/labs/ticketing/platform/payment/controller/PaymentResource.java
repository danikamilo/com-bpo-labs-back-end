package com.back.bpo.labs.ticketing.platform.payment.controller;

import com.back.bpo.labs.ticketing.platform.payment.model.Payment;
import com.back.bpo.labs.ticketing.platform.payment.service.IPaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentResource.class);

    @Inject
    private IPaymentService paymentService;

    // Endpoint to list all payments
    @GET
    public Response list() {
        try {
            List<Payment> payments = paymentService.listAll();
            return Response.ok(payments).build(); // Return the list of payments if successful
        } catch (Exception e) {
            LOGGER.error("Error while fetching the list of payments", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while fetching the list of payments: " + e.getMessage()) // Return error message if an exception occurs
                    .build();
        }
    }

    // Endpoint to process a payment
    @POST
    public Response pay(Payment payment) {
        try {
            Payment processedPayment = paymentService.process(payment);
            return Response.status(Response.Status.CREATED).entity(processedPayment).build(); // Return the processed payment if successful
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while processing the payment", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error while processing the payment: " + e.getMessage()) // Return error message for JSON processing failure
                    .build();
        } catch (Exception e) {
            LOGGER.error("Unexpected error while processing the payment", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Unexpected error while processing the payment: " + e.getMessage()) // Return general error message for unexpected exceptions
                    .build();
        }
    }

    // Endpoint to find a payment by its ID
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") String id) {
        try {
            Payment payment = paymentService.findById(id);
            if (payment == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Payment not found for id: " + id) // Return a not found message if the payment is not found
                        .build();
            }
            return Response.ok(payment).build(); // Return the payment if found
        } catch (Exception e) {
            LOGGER.error("Error while fetching the payment with id: " + id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error while fetching the payment: " + e.getMessage()) // Return error message for any exception
                    .build();
        }
    }
}