package com.back.bpo.labs.ticketing.platform.notification.service.impl;

import com.back.bpo.labs.ticketing.platform.libs.exceptions.ExceptionUtil;
import com.back.bpo.labs.ticketing.platform.notification.model.Notification;
import com.back.bpo.labs.ticketing.platform.notification.repository.NotificationRepository;
import com.back.bpo.labs.ticketing.platform.notification.service.INotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class NotificationServiceImpl implements INotificationService {

    @Inject
    private NotificationRepository notificationRepository;

    public List<Notification> listAll() {
        return notificationRepository.listAll();
    }

    public Notification send(Notification notification) {
        try {
            notificationRepository.persist(notification);
            return notification;
        } catch (Exception e) {
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }
}
