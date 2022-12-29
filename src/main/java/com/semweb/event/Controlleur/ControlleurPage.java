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
    
    @RequestMapping("/")
    public String mainredirection(Model model){
        Event lesson = GetRDF.GetNextCourse();
        model.addAttribute("lesson", lesson);
        return "main";
    }
    @PostMapping("allEvents/")
    public String allEventsDirection(Model model)
    {
        // String queryString = "SELECT ?subject ?predictat ?object \n WHERE { \n ?subject ?predictat ?object \n}";
        // Query query = QueryFactory.create(queryString);
        // QueryEngineHTTP qexec = new QueryEngineHTTP("https://territoire.emse.fr/ldp/maximeaurelien/", query );

        // ResultSet results = qexec.execSelect();
        // List resultsList = ResultSetFormatter.toList(results);
        // qexec.close();

        List<Event> events = GetRDF.GetAllEvent();
        model.addAttribute("events", events);
        return "allEvents";
    }

    @PostMapping("EventsSainte/")
    public String allEventsSainte(Model model)
    {
        List<Event> events = GetRDF.GetEventSainte();
        model.addAttribute("events", events);
        return "allEvents";
    }
    @PostMapping("NotEventsSainte/")
    public String allNotEventsSainte(Model model)
    {
        List<Event> events = GetRDF.GetEventNotInSainte();
        model.addAttribute("events", events);
        return "allEvents";
    }

    @PostMapping("NotCourse/")
    public String allNotCourse(Model model)
    {
        List<Event> events = GetRDF.GetEventNotCourse();
        model.addAttribute("events", events);
        return "allEvents";
    }

    @PostMapping("Course/")
    public String allCourse(Model model)
    {
        List<Event> events = GetRDF.GetEventCourse();
        model.addAttribute("events", events);
        return "allEvents";
    }

    @PostMapping("EventsSainteNotCourse/")
    public String allEventsSainteNotCourse(Model model)
    {
        List<Event> events = GetRDF.GetEventSainteNotCourse();
        model.addAttribute("events", events);
        return "allEvents";
    }
}
