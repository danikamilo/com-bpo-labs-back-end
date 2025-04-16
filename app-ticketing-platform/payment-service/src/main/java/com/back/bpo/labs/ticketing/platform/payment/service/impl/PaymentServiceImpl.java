package com.back.bpo.labs.ticketing.platform.payment.service.impl;

import com.back.bpo.labs.ticketing.platform.payment.model.Payment;
import com.back.bpo.labs.ticketing.platform.payment.repository.PaymentRepository;
import com.back.bpo.labs.ticketing.platform.payment.service.IPaymentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class PaymentServiceImpl implements IPaymentService {

    @Inject
    private PaymentRepository repository;

    public List<Payment> listAll() {
        return repository.listAll();
    }

    public void process(Payment payment) {
        payment.status = "PAID";
        repository.persist(payment);
    }

    public Payment findById(String id) {
        return repository.findById(new org.bson.types.ObjectId(id));
    }
}
