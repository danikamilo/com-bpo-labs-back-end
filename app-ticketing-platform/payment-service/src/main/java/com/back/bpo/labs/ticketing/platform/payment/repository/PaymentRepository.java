package com.back.bpo.labs.ticketing.platform.payment.repository;

import com.back.bpo.labs.ticketing.platform.payment.model.Payment;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class PaymentRepository implements PanacheMongoRepository<Payment> {
}