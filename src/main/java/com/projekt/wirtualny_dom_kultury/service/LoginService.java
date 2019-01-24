package com.projekt.wirtualny_dom_kultury.service;

import com.projekt.wirtualny_dom_kultury.model.AppUser;
import com.projekt.wirtualny_dom_kultury.model.UserRole;
import com.projekt.wirtualny_dom_kultury.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(username);
        if (appUserOptional.isPresent()) {
            AppUser appUser = appUserOptional.get();

            // todo: uprawnienia
            Set<String> grantedAuthoritySet = new HashSet<>();
            // pobieramy role użytkownika
            for (UserRole role : appUser.getRoles()) {
                // zamieniamy role na zbiór String'ów
                grantedAuthoritySet.add(role.getName().replace("ROLE_", ""));
            }
            // umieszczamy go w tablicy ( którą dalej trzeba przekazać do obiektu User )
            String[] authorities = grantedAuthoritySet.toArray(new String[grantedAuthoritySet.size()]);

            // za porównanie hasła odpowiada spring
            return User.builder()
                    .username(appUser.getUsername())
                    .password(appUser.getPassword())
                    .disabled(false)
                    .roles(authorities)
                    .build();
        }
        throw new UsernameNotFoundException("Username: " + username + " could not be found.");
    }
}
