package com.projekt.wirtualny_dom_kultury.configuration;

import com.projekt.wirtualny_dom_kultury.model.AppUser;
import com.projekt.wirtualny_dom_kultury.model.UserRole;
import com.projekt.wirtualny_dom_kultury.repository.AppUserRepository;
import com.projekt.wirtualny_dom_kultury.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        checkAndLoadRoles();
        checkAndLoadUsers();
    }
    private void checkAndLoadUsers() {
        if (!checkUser("admin")) {
            createUser("admin", "admin", "ROLE_ADMIN");
        }
        // dodaj user'a!
//        if (!checkUser("user")) {
//            createUser("user", "user", "ROLE_USER");
//        }
//        if (!checkUser("organizer")) {
//            createUser("organizer", "organizer", "ROLE_USER", "ROLE_EVENT_ORGANIZER");
//        }
    }

    private void createUser(String username, String password, String... roles) {
//        AppUser appUser = new AppUser();
//        appUser.setUsername(username);
//        appUser.setPassword(password);

        // odnajdujemy w bazie danych wszystkie uprawnienia które należy nadać użytkownikowi
        Set<UserRole> userRoles = new HashSet<>();
        for (String role : roles) {
            userRoles.add(findRole(role));
            // zbieramy uprawnienia do setu
        }

        // tworzymy instancję użytkownika
        AppUser appUser = AppUser.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .roles(userRoles)
                .build();

        // zapisujemy instancję w bazie
        appUserRepository.saveAndFlush(appUser);
    }

    private UserRole findRole(String role) {
        Optional<UserRole> userRoleOptional = userRoleRepository.findByName(role);
        if (userRoleOptional.isPresent()) {
            UserRole userRole = userRoleOptional.get();

            return userRole;
        }
        throw new DataIntegrityViolationException("User role does not exist. " +
                "Try fixing user role called: " + role + " in Your data initializer.");
    }

    private boolean checkUser(String username) {
        return appUserRepository.findByUsername(username).isPresent();
    }

    private void checkAndLoadRoles() {
        if (!checkRole("ROLE_USER")) {
            createRole("ROLE_USER");
        }
        if (!checkRole("ROLE_ADMIN")) {
            createRole("ROLE_ADMIN");
        }
        if (!checkRole("ROLE_EVENT_ORGANIZER")) {
            createRole("ROLE_EVENT_ORGANIZER");
        }
    }

    /**
     * Tworzenie roli użytkownika.
     *
     * @param role - nazwa roli która ma być stworzona.
     */
    private void createRole(String role) {
        UserRole createdRole = new UserRole(null, role);
        userRoleRepository.saveAndFlush(createdRole);
    }

    /**
     * Sprawdzenie czy rola istnieje.
     *
     * @param role - nazwa roli która ma być sprawdzona.
     * @return - wynik true - jeśli rola istnieje, false jeśli nie.
     */
    private boolean checkRole(String role) {
        return userRoleRepository.findByName(role).isPresent();
    }


}
