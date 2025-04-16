package com.back.bpo.labs.ticketing.platform.inventory.repository;

import com.back.bpo.labs.ticketing.platform.inventory.model.Inventory;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class InventoryRepository implements PanacheMongoRepository<Inventory> {
}