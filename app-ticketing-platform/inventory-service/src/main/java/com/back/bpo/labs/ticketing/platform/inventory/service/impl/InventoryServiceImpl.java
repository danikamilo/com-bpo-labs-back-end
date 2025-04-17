package com.back.bpo.labs.ticketing.platform.inventory.service.impl;

import com.back.bpo.labs.ticketing.platform.inventory.model.Inventory;
import com.back.bpo.labs.ticketing.platform.inventory.repository.InventoryRepository;
import com.back.bpo.labs.ticketing.platform.inventory.service.IInventoryService;
import com.back.bpo.labs.ticketing.platform.libs.exceptions.ExceptionUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class InventoryServiceImpl implements IInventoryService {

    @Inject
    private InventoryRepository repository;

    public List<Inventory> listAll() {
        return repository.listAll();
    }

    public Inventory findByEvent(String eventId) {
        try {
            return repository.find("eventId", eventId).firstResult();
        } catch (Exception e) {
            throw ExceptionUtil.handlePersistenceException(e);
        }

    }

    public Inventory addInventory(Inventory inventory) {
        try {
            repository.persist(inventory);
            return inventory;
        } catch (Exception e) {
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    public boolean reserveTicket(String eventId, int quantity) {
        try {
            Inventory inv = findByEvent(eventId);
            if (inv != null && inv.availableTickets >= quantity) {
                inv.availableTickets -= quantity;
                return true;
            }
            return false;
        } catch (Exception e) {
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }
}
