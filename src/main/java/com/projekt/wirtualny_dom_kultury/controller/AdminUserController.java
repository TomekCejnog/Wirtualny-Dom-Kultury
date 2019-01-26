package com.projekt.wirtualny_dom_kultury.controller;

import com.projekt.wirtualny_dom_kultury.model.AppUser;
import com.projekt.wirtualny_dom_kultury.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private AppUserService appUserService;

    // adres na który trzeba wejść:
    // /admin/users
    @GetMapping("/users")
    public String getUserList(Model model) {

        List<AppUser> users = appUserService.getAllUsers();

        model.addAttribute("user_list", users);

        model.addAttribute("user_roles", users.stream()
                .map(user -> user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()))
                // w wyniku powyższej instrukcji otrzymaliśmy stream list (ze stringami) uprawnień użytkowników
                .map(String::valueOf)
                // zamieniam każdą listę na tekst
                .collect(Collectors.toList()));

        // zwracamy admin (bo plik html znajduje się w katalogu admin)
        // a po nim /userlist (bo plik html nazywa się userlist)
        return "admin/userlist";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public String removeUser(@RequestParam(name = "userToRemoveId") Long id){
    appUserService.remove(id);
//    return "admin/userlist";
        return "redirect:/admin/users";
    }


}
