package com.projekt.wirtualny_dom_kultury.service;

import com.projekt.wirtualny_dom_kultury.model.AppUser;
import com.projekt.wirtualny_dom_kultury.model.Event;
import com.projekt.wirtualny_dom_kultury.model.Reservation;
import com.projekt.wirtualny_dom_kultury.repository.AppUserRepository;
import com.projekt.wirtualny_dom_kultury.repository.EventRepository;
import com.projekt.wirtualny_dom_kultury.repository.ReservationRepository;
import com.projekt.wirtualny_dom_kultury.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean register(String username,
                            String password,
                            String name,
                            String lastName,
                            String email,
                            String phoneNumber, boolean isOrganizer) {
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUser.setName(name);
        appUser.setLastName(lastName);
        appUser.setEmail(email);
        appUser.setPhoneNumber(phoneNumber);
        if (isOrganizer == true) {
            appUser.getRoles().add(userRoleService.getOrganizerRole());

        } else {
            appUser.getRoles().add(userRoleService.getUserRole());
        }

        try {
            appUserRepository.saveAndFlush(appUser);
        } catch (ConstraintViolationException cve) {
            return false;
        }
        return true;
    }

    public void remove(Long id) {
        AppUser user = appUserRepository.getOne(id);
        for (Event e : user.getEventList()) {
            eventRepository.deleteById(e.getId());
        }
        for (Reservation r : user.getReservations()) {
            reservationRepository.delete(r);
        }
        appUserRepository.deleteById(id);
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }


    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public void save(AppUser optionaAppuser) {
        appUserRepository.save(optionaAppuser);
    }
}
