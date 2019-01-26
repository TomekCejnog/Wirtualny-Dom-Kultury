package com.projekt.wirtualny_dom_kultury.controller;

import com.projekt.wirtualny_dom_kultury.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping("/")
    public String getIndex (){
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage (){
        return "login";
    }

    @GetMapping ("/register")
    public String getRegisterPage (){
        return "register";
    }

    @PostMapping("/register")
    public String submitRegistrationForm (String username,
                                          String password,
                                          String name,
                                          String lastName,
                                          String email,
                                          String phoneNumber,
                                          String passwordConfirm,
                                          boolean isOrganizer){
    if(!password.equals(passwordConfirm)){
        return "redirect:/register?error=Password do not match";
    }
    boolean result = appUserService.register(username, password, name, lastName, email, phoneNumber, isOrganizer);
    if(!result){
        return "redirect:/register?error=Error while registration. Probably username exists.";
    }
    return "redirect:/admin/users";
    }
}
