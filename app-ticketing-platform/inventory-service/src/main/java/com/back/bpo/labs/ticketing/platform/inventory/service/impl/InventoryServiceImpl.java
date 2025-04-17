package com.back.bpo.labs.ticketing.platform.inventory.service.impl;

import com.back.bpo.labs.ticketing.platform.inventory.model.Inventory;
import com.back.bpo.labs.ticketing.platform.inventory.repository.InventoryRepository;
import com.back.bpo.labs.ticketing.platform.inventory.service.IInventoryService;
import com.back.bpo.labs.ticketing.platform.libs.exceptions.ExceptionUtil;
import com.back.bpo.labs.ticketing.platform.libs.exceptions.NoContentException;
import com.back.bpo.labs.ticketing.platform.libs.exceptions.TicketsNotAvailableException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

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

    public Inventory reserveTicket(String eventId, int quantity) {
        try {
            Inventory inv = Optional.ofNullable(findByEvent(eventId)).orElseThrow(() -> new NoContentException("La consulta a las base de datos no devolvio datos."));
            if (inv.getAvailableTickets() < quantity)
                throw new TicketsNotAvailableException("No hay suficientes tickets disponibles para este evento.");

            inv.setAvailableTickets(inv.getAvailableTickets() - quantity);
            inv.update();
            return inv;
        } catch (Exception e) {
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

}
