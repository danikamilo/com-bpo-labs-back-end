package com.back.bpo.labs.ticketing.platform.event.service;

import com.back.bpo.labs.ticketing.platform.event.model.Event;

import java.util.List;

/**
 * @author Daniel Camilo
 */
public interface IEventService {

    /**
     *
     * @return List<Event>
     */
    public List<Event> getAllEvents();


    /**
     *
     * @param event
     * @return
     */
    public Event createEvent(Event event);
}
