package com.back.bpo.labs.ticketing.platform.inventory.service;

import com.back.bpo.labs.ticketing.platform.inventory.model.Inventory;
import java.util.List;

/**
 * @author Daniel Camilo
 */
public interface IInventoryService {

    /**
     *
     * @return
     */
    public List<Inventory> listAll();

    /**
     *
     * @param eventId
     * @return
     */
    public Inventory findByEvent(String eventId);

    /***
     *
     * @param inventory
     */
    public Inventory addInventory(Inventory inventory);

    /**
     *
     * @param eventId
     * @param quantity
     * @return
     */
    public Inventory reserveTicket(String eventId, int quantity);

}
