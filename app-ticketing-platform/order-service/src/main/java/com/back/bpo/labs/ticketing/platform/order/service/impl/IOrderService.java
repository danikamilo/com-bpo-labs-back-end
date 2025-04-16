package com.back.bpo.labs.ticketing.platform.order.service.impl;


import com.back.bpo.labs.ticketing.platform.order.model.Order;

import java.util.List;

/**
 * @autho Daniel Camilo
 */
public interface IOrderService {


    /**
     *
     * @return
     */
    public List<Order> listAll();

    /**
     *
     * @param order
     */
    public void create(Order order);

    /**
     *
     * @param id
     * @return
     */
    public Order findById(String id);
}
