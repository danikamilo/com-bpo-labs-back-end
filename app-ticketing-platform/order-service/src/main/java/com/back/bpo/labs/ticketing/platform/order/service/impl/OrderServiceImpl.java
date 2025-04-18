package com.back.bpo.labs.ticketing.platform.order.service.impl;

import com.back.bpo.labs.ticketing.platform.libs.enums.Status;
import com.back.bpo.labs.ticketing.platform.libs.exceptions.ExceptionUtil;
import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.OrderEventDTO;
import com.back.bpo.labs.ticketing.platform.libs.kafka.producer.OrderEventProducer;
import com.back.bpo.labs.ticketing.platform.libs.utils.KafkaEventMapper;
import com.back.bpo.labs.ticketing.platform.order.model.Order;
import com.back.bpo.labs.ticketing.platform.order.repository.OrderRepository;
import com.back.bpo.labs.ticketing.platform.order.service.IOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Inject
    private OrderEventProducer eventProducer;

    public List<Order> listAll() {
        return repository.listAll();
    }

    public Order create(Order order) {
        try {
            repository.persist(order);
            sendOrderMessage(order, Status.CREATED.getValue());
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

    public void sendOrderMessage(Order order, String message) throws JsonProcessingException {
        OrderEventDTO dto = new OrderEventDTO();
        dto.setOrderId(order.getUserId());
        dto.setEventType(message);
        dto.setPayload(KafkaEventMapper.toJson(order));
        eventProducer.sendOrderEvent(dto);
    }

}