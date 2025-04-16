package com.back.bpo.labs.ticketing.platform.event.service.impl;

import com.back.bpo.labs.ticketing.platform.event.model.Event;
import com.back.bpo.labs.ticketing.platform.event.repository.EventRepository;
import com.back.bpo.labs.ticketing.platform.event.service.IEventService;
import com.back.bpo.labs.ticketing.platform.libs.exceptions.ExceptionUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

/**
 * @author Daniel Camilo
 */
@ApplicationScoped
public class EventServiceImpl implements IEventService {

    @Inject
    private EventRepository eventRepository;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.listAll();
    }

    @Override
    public Boolean createEvent(Event event) {
        try {
            eventRepository.persist(event);
            return true;
        } catch (Exception e) {
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }
}
