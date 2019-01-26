package com.projekt.wirtualny_dom_kultury.controller;

import com.projekt.wirtualny_dom_kultury.model.AppUser;
import com.projekt.wirtualny_dom_kultury.model.Event;
import com.projekt.wirtualny_dom_kultury.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/organizer")
public class OrgaznizerEventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/eventAdd")
    public String getEventForm (){
        return "organizer/eventsForm";
    }

    @PostMapping("/eventAdd")
    public String postEventForm(String eventName,
                                int eventLenght,
                                int accessibility,
                                String description,
                                String date){

        LocalDate dateFormatted = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        eventService.registerEvent(eventName, eventLenght, accessibility, description,dateFormatted );
        return "redirect:/organizer/eventList";}

    @GetMapping("/eventList")
    public String getEventList(Model model) {

        List<Event> events = eventService.getAllEvents();

        model.addAttribute("event_list", events);

        return "organizer/eventList";
    }
    @RequestMapping(value = "/removeEvent", method = RequestMethod.GET)
    public String removeEvent(@RequestParam(name = "eventToRemoveId") Long id){
        eventService.remove(id);
//    return "admin/userlist";
        return "redirect:/organizer/eventList";
    }
}
