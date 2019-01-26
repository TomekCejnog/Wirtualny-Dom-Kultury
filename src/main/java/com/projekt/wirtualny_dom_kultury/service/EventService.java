package com.projekt.wirtualny_dom_kultury.service;

import com.projekt.wirtualny_dom_kultury.model.AppUser;
import com.projekt.wirtualny_dom_kultury.model.Event;
import com.projekt.wirtualny_dom_kultury.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;


    public boolean registerEvent(String eventName,
                                 int eventLenght,
                                 int accessibility,
                                 String description,
                                 LocalDate date) {
        Event event = new Event();
        event.setEventName(eventName);
        event.setEventLenght(eventLenght);
        event.setAccessibility(accessibility);
        event.setDescription(description);
        event.setDate(date);

        try {
            eventRepository.saveAndFlush(event);
        } catch (ConstraintViolationException cve) {
            return false;
        }
        return true;
    }
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void remove (Long id){
        eventRepository.deleteById(id);
    }
}
