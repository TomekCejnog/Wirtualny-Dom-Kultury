package com.projekt.wirtualny_dom_kultury.controller;

import com.projekt.wirtualny_dom_kultury.model.Event;
import com.projekt.wirtualny_dom_kultury.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/eventUser")
public class UserEventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/eventUserList")
    public String getEventList(Model model) {

        List<Event> events = eventService.getAllEvents();

        model.addAttribute("event_user_list", events);

        return "virtualCommunityUser/eventUserList";
    }

    @GetMapping("/reserve/{id}")
    public String reserve(@PathVariable(name = "id") Long eventId){
        eventService.makeReservation(eventId);
        return "redirect:/eventUser/eventUserList";
    }

}
