package com.semweb.event.Controlleur;
import com.semweb.event.DAL.GetRDF;
import com.semweb.event.Model.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Controller
public class ControlleurPage {
    /**
     * Ces fonctions gèrent les changements de pages HTML
     * @param model le modèle content la liste des RDF
     * @return la page HTML correspondante
     */
    @RequestMapping("/")
    public String mainredirection(Model model){
        Event lesson = GetRDF.GetNextCourse();
        model.addAttribute("lesson", lesson);
        return "main";
    }
    @PostMapping("allEvents/")
    public String allEventsDirection(Model model)
    {
        List<Event> events = GetRDF.GetAllEvent();
        model.addAttribute("events", events);
        return "allEvents";
    }

    @PostMapping("EventsSainte/")
    public String allEventsSainte(Model model)
    {
        List<Event> events = GetRDF.GetEventSainte();
        model.addAttribute("events", events);
        return "SainteEvents";
    }
    @PostMapping("NotEventsSainte/")
    public String allNotEventsSainte(Model model)
    {
        List<Event> events = GetRDF.GetEventNotInSainte();
        model.addAttribute("events", events);
        return "notSainteEvents";
    }

    @PostMapping("NotCourse/")
    public String allNotCourse(Model model)
    {
        List<Event> events = GetRDF.GetEventNotCourse();
        model.addAttribute("events", events);
        return "NotCourseEvent";
    }

    @PostMapping("Course/")
    public String allCourse(Model model)
    {
        List<Event> events = GetRDF.GetEventCourse();
        model.addAttribute("events", events);
        return "CourseEvent";
    }

    @PostMapping("EventsSainteNotCourse/")
    public String allEventsSainteNotCourse(Model model)
    {
        List<Event> events = GetRDF.GetEventSainteNotCourse();
        model.addAttribute("events", events);
        return "SainteEventsNotCourse";
    }
}
