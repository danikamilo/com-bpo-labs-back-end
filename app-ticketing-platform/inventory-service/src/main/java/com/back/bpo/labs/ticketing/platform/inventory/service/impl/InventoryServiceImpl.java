package com.back.bpo.labs.ticketing.platform.inventory.service.impl;

import com.back.bpo.labs.ticketing.platform.inventory.model.Inventory;
import com.back.bpo.labs.ticketing.platform.inventory.repository.InventoryRepository;
import com.back.bpo.labs.ticketing.platform.inventory.service.IInventoryService;
import com.back.bpo.labs.ticketing.platform.libs.enums.Status;
import com.back.bpo.labs.ticketing.platform.libs.exceptions.ExceptionUtil;
import com.back.bpo.labs.ticketing.platform.libs.exceptions.NoContentException;
import com.back.bpo.labs.ticketing.platform.libs.exceptions.TicketsNotAvailableException;
import com.back.bpo.labs.ticketing.platform.libs.kafka.dto.OrderEventDTO;
import com.back.bpo.labs.ticketing.platform.libs.kafka.producer.OrderEventProducer;
import com.back.bpo.labs.ticketing.platform.libs.utils.KafkaEventMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class InventoryServiceImpl implements IInventoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Inject
    private InventoryRepository repository;

    @Inject
    private OrderEventProducer eventProducer;

    public List<Inventory> listAll() {
        try {
            List<Inventory> list = repository.listAll();
            LOGGER.info("Fetched {} inventory items", list.size());
            return list;
        } catch (Exception e) {
            LOGGER.error("Error listing inventory items", e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    public Inventory findByEvent(String eventId) {
        try {
            Inventory inv = repository.find("eventId", eventId).firstResult();
            LOGGER.info("Inventory found for eventId={}: {}", eventId, inv);
            return inv;
        } catch (Exception e) {
            LOGGER.error("Error finding inventory by eventId={}", eventId, e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    public Inventory addInventory(Inventory inventory) {
        try {
            repository.persist(inventory);
            sendOrderMessage(inventory, Status.CREATED.getValue());
            LOGGER.info("Inventory created and message sent: {}", inventory);
            return inventory;
        } catch (Exception e) {
            LOGGER.error("Error adding inventory: {}", inventory, e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    public Inventory reserveTicket(String eventId, int quantity) {
        try {
            Inventory inv = Optional.ofNullable(findByEvent(eventId))
                    .orElseThrow(() -> new NoContentException("No inventory found for eventId=" + eventId));

            if (inv.getAvailableTickets() < quantity) {
                LOGGER.warn("Not enough tickets available for eventId={}. Requested={}, Available={}", eventId, quantity, inv.getAvailableTickets());
                throw new TicketsNotAvailableException("No hay suficientes tickets disponibles para este evento.");
            }

            inv.setAvailableTickets(inv.getAvailableTickets() - quantity);
            inv.update();
            sendOrderMessage(inv, Status.INVENTORY_UPDATED.getValue());

            LOGGER.info("Tickets reserved for eventId={}. Remaining={}", eventId, inv.getAvailableTickets());
            return inv;
        } catch (Exception e) {
            LOGGER.error("Error reserving ticket for eventId={}", eventId, e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    public void sendOrderMessage(Inventory event, String message) throws JsonProcessingException {
        OrderEventDTO dto = new OrderEventDTO();
        dto.setOrderId(event.getEventId());
        dto.setEventType(message);
        dto.setPayload(KafkaEventMapper.toJson(event));
        eventProducer.sendOrderEvent(KafkaEventMapper.toJson(event));
        LOGGER.debug("OrderEvent sent to Kafka: {}", dto);
    }
}