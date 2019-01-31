package com.projekt.wirtualny_dom_kultury.service;

import com.projekt.wirtualny_dom_kultury.model.AppUser;
import com.projekt.wirtualny_dom_kultury.model.Event;
import com.projekt.wirtualny_dom_kultury.model.Reservation;
import com.projekt.wirtualny_dom_kultury.repository.EventRepository;
import com.projekt.wirtualny_dom_kultury.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EventService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ReservationRepository reservationRepository;

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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<AppUser> appUser = appUserService.findByUsername(user.getUsername());
        AppUser optionaAppuser = appUser.get();

        event.setOwner(optionaAppuser);

        eventRepository.save(event);
        optionaAppuser.getEventList().add(event);
        appUserService.save(optionaAppuser);
        return true;
    }

    public List<Event> getEventsOfThisUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<AppUser> appUser = appUserService.findByUsername(user.getUsername());
        AppUser optionaAppuser = appUser.get();

        return optionaAppuser.getEventList();
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void remove (Long id){
        eventRepository.deleteById(id);
    }

    public void makeReservation(Long eventId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<AppUser> appUser = appUserService.findByUsername(user.getUsername());
        AppUser optionaAppuser = appUser.get();

        Event event = eventRepository.getOne(eventId);

        // todo: sprawdz czy dany event nie zawiera juz rezerwacji na tego uzytkownika

        Reservation reservation = new Reservation(null, optionaAppuser, false, false);
        reservation = reservationRepository.save(reservation);
        event.getReservations().add(reservation);

        eventRepository.save(event);

        optionaAppuser.getReservations().add(reservation);
        appUserService.save(optionaAppuser);
    }

    public Set<Reservation> getEventWithId(Long eventId) {
        return eventRepository.getOne(eventId).getReservations();
    }

    public void approve(Long userId, Long eventId) {
        System.out.println("Approved");
        //todo: znalezc event po id w repozytorium
        // przeszukac rezerwacje tego eventu
        // znalezc uzytkownika
        // zatwierdzic rezerwacje

        // zapisac event!
        // zapisac uzytkownika!
    }
}
