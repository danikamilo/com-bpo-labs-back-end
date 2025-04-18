package com.back.bpo.labs.ticketing.platform.payment.service;

import com.back.bpo.labs.ticketing.platform.payment.model.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * @author Daniel Camilo
 */
public interface IPaymentService {

    /**
     *
     * @return
     */
    public List<Payment> listAll() ;

    /**
     *
     * @param payment
     */
    public Payment process(Payment payment) throws JsonProcessingException;

    /**
     *
     * @param id
     * @return
     */
    public Payment findById(String id);
}
