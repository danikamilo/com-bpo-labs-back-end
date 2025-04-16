package com.back.bpo.labs.ticketing.platform.order.service.impl;

import com.back.bpo.labs.ticketing.platform.order.model.Order;
import com.back.bpo.labs.ticketing.platform.order.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class OrderServiceImpl implements IOrderService {

    @Inject
    private OrderRepository repository;

    public List<Order> listAll() {
        return repository.listAll();
    }

    public void create(Order order) {
        repository.persist(order);
    }

    public Order findById(String id) {
        return repository.findById(new org.bson.types.ObjectId(id));
    }
}