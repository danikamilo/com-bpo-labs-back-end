package com.back.bpo.labs.ticketing.platform.notification.service.impl;

import com.back.bpo.labs.ticketing.platform.libs.exceptions.ExceptionUtil;
import com.back.bpo.labs.ticketing.platform.notification.model.Notification;
import com.back.bpo.labs.ticketing.platform.notification.repository.NotificationRepository;
import com.back.bpo.labs.ticketing.platform.notification.service.INotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class NotificationServiceImpl implements INotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Inject
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> listAll() {
        try {
            List<Notification> notifications = notificationRepository.listAll();
            LOGGER.info("Fetched {} notifications successfully", notifications.size());
            return notifications;
        } catch (Exception e) {
            LOGGER.error("Error fetching notifications", e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    @Override
    public Notification send(Notification notification) {
        try {
            notificationRepository.persist(notification);
            LOGGER.info("Notification sent and persisted successfully: {}", notification);
            return notification;
        } catch (Exception e) {
            LOGGER.error("Error sending notification: {}", notification, e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }
}