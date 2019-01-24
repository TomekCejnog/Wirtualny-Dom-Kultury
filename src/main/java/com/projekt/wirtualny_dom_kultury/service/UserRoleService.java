package com.projekt.wirtualny_dom_kultury.service;

import com.projekt.wirtualny_dom_kultury.model.UserRole;
import com.projekt.wirtualny_dom_kultury.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole getUserRole() {
        Optional<UserRole> optionalUserRole = userRoleRepository.findByName("ROLE_USER");
        if(optionalUserRole.isPresent()){
            return optionalUserRole.get();
        }
        throw  new DataIntegrityViolationException("USER_ROLE should exist in database.");
    }

    public UserRole getOrganizerRole() {
        Optional<UserRole> optionalUserRole = userRoleRepository.findByName("ROLE_EVENT_ORGANIZER");
        if(optionalUserRole.isPresent()){
            return optionalUserRole.get();
        }
        throw  new DataIntegrityViolationException("USER_ROLE should exist in database.");
    }


}
