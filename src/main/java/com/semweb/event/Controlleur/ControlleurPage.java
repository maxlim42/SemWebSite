package com.semweb.event.Controlleur;
import com.semweb.event.DAL.GetRDF;
import com.semweb.event.Model.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ControlleurPage {
    
    @RequestMapping("/")
    public String mainredirection()
    {
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
    
}
