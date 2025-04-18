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
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class OrderServiceImpl implements IOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Inject
    OrderRepository repository;

    @Inject
    OrderEventProducer eventProducer;

    @Override
    public List<Order> listAll() {
        try {
            List<Order> orders = repository.listAll();
            LOGGER.info("Fetched {} orders", orders.size());
            return orders;
        } catch (Exception e) {
            LOGGER.error("Error listing orders", e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    @Override
    public Order create(Order order) {
        try {
            repository.persist(order);
            LOGGER.info("Order created: {}", order);
            sendOrderMessage(order, Status.CREATED.getValue());
            return order;
        } catch (Exception e) {
            LOGGER.error("Error creating order: {}", order, e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    @Override
    public Order findById(String id) {
        try {
            Order order = repository.findById(new ObjectId(id));
            LOGGER.info("Order found: {}", order);
            return order;
        } catch (Exception e) {
            LOGGER.error("Error finding order by ID: {}", id, e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    private void sendOrderMessage(Order order, String message) {
        try {
            OrderEventDTO dto = new OrderEventDTO();
            dto.setOrderId(order.getOrderId());
            dto.setEventType(message);
            dto.setPayload("");
            LOGGER.info("Sending Kafka order event: {}", dto);
            eventProducer.sendOrderEvent(KafkaEventMapper.toJson(order));
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to serialize order for Kafka event", e);
            throw new RuntimeException("Failed to serialize order", e);
        }
    }
}