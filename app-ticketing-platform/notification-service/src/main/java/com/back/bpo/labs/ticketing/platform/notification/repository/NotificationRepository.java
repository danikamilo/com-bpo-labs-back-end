package com.back.bpo.labs.ticketing.platform.notification.repository;

import com.back.bpo.labs.ticketing.platform.notification.model.Notification;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;


/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class NotificationRepository implements PanacheMongoRepository<Notification> {
}
