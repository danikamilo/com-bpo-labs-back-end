package com.back.bpo.labs.ticketing.platform.event.service.impl;

import com.back.bpo.labs.ticketing.platform.event.model.Event;
import com.back.bpo.labs.ticketing.platform.event.repository.EventRepository;
import com.back.bpo.labs.ticketing.platform.event.service.IEventService;
import com.back.bpo.labs.ticketing.platform.libs.exceptions.ExceptionUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
public class EventServiceImpl implements IEventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    @Inject
    private EventRepository eventRepository;

    @Override
    public List<Event> getAllEvents() {
        try {
            List<Event> events = eventRepository.listAll();
            LOGGER.info("Fetched {} events", events.size());
            return events;
        } catch (Exception e) {
            LOGGER.error("Error fetching all events", e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }

    @Override
    public Event createEvent(Event event) {
        try {
            eventRepository.persist(event);
            LOGGER.info("Event created: {}", event);
            return event;
        } catch (Exception e) {
            LOGGER.error("Error creating event: {}", event, e);
            throw ExceptionUtil.handlePersistenceException(e);
        }
    }
}