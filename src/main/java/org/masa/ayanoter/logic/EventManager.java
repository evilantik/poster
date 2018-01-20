package org.masa.ayanoter.logic;

import org.masa.ayanoter.dataAccess.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventManager implements IEventManager {
    private EventRepository eventRepository;

    @Autowired
    public EventManager(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }



    @Override
    public List<Event> getEventsOfUsers(List<User> users) {
        return eventRepository.findByAuthorInOrderByDateDesc(users);
    }
}
