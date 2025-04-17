package com.back.bpo.labs.ticketing.platform.order.service.impl;

import com.back.bpo.labs.ticketing.platform.libs.exceptions.ExceptionUtil;
import com.back.bpo.labs.ticketing.platform.order.model.Order;
import com.back.bpo.labs.ticketing.platform.order.repository.OrderRepository;
import com.back.bpo.labs.ticketing.platform.order.service.IOrderService;
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

    public Order create(Order order) {
        try {
            repository.persist(order);
            return order;
        } catch (Exception e) {
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    public Order findById(String id) {
        try {
            return repository.findById(new org.bson.types.ObjectId(id));
        } catch (Exception e) {
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }
}