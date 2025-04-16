package com.back.bpo.labs.ticketing.platform.order.repository;


import com.back.bpo.labs.ticketing.platform.order.model.Order;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class OrderRepository implements PanacheMongoRepository<Order> {
}