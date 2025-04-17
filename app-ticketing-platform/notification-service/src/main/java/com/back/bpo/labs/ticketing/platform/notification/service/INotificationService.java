package com.back.bpo.labs.ticketing.platform.notification.service;

import com.back.bpo.labs.ticketing.platform.notification.model.Notification;

import java.util.List;

/**
 * @author Daniel Camilo
 */
public interface INotificationService {

    /**
     *
     * @return
     */
    public List<Notification> listAll();

    /**
     * @param notification
     * @return
     */
    public Notification send(Notification notification);
}
