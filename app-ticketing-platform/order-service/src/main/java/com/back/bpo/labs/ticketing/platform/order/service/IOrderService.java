package com.back.bpo.labs.ticketing.platform.order.service;


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
     * @param order
     * @return
     */
    public Order create(Order order);

    /**
     *
     * @param id
     * @return
     */
    public Order findById(String id);
}
