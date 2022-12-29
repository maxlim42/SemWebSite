package com.semweb.event.DAL;

import com.semweb.event.Model.Event;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdfconnection.RDFConnection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GetRDF {
    public static List<Event> GetAllEvent(){

        List<Event> events = new ArrayList<Event>();
        try (RDFConnection conn = RDFConnection.connectPW(
                "https://territoire.emse.fr/ldp/maximeaurelien/",
                "ldpuser",
                "LinkedDataIsGreat"
        )) {

            Model model = conn.queryConstruct("CONSTRUCT {?s ?p ?o} WHERE {?s ?p ?o. filter (contains(str(?s), \"https://territoire.emse.fr/ldp/maximeaurelien/\"))}");
            try {
                StmtIterator statements = model.listStatements();

                try {
                    List stats = statements.toList();
                    String lastsubject = "";
                    Event event = new Event();
                    for (Object stat:stats) {
                        System.out.println(stat);
                        String subject = stat.toString().split(",")[0].replace("[","").trim();
                        String predicate = stat.toString().split(",")[1].trim();
                        String object = stat.toString().split(",")[2].replace("]","").trim();

                        String [] statSplit = stat.toString().split(",");

                        if(statSplit.length > 3)
                        {
                            for (int i =3; i <statSplit.length; i++){
                                object = object + " " + statSplit[i];
                            }
                        }

                        if(!subject.equals(lastsubject) && lastsubject != ""){

                            if(event.Name == null || event.Name.contains("chevalier"))
                            {
                                int a =5;
                            }
                            if(event.Categorie != null
                                    && event.Description != null
                                    && event.EndDate != null
                                    && event.StartDate != null
                                    && event.Name != null
                                    && event.Location != null
                                    && event.Organizer != null){
                                events.add(event);
                                event = new Event();
                            }
                        }

                            if(predicate.contains("additionalType")){
                                String[] objectSplit = object.split("/");
                                event.Categorie = objectSplit[objectSplit.length -1];
                            }
                            if(predicate.contains("startDate")){
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                                event.StartDate = LocalDateTime.parse(object.substring(1,20), formatter);
                            }
                            if(predicate.contains("endDate")){
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                                event.EndDate = LocalDateTime.parse(object.substring(1,20), formatter);
                            }
                            if(predicate.contains("location")){
                                event.Location = object.replace("\"", "").trim();
                            }
                            if(predicate.contains("label")){
                                event.Name = object.replace("\"", "").replace("]", "").trim();
                            }
                            if(predicate.contains("organizer")){
                                event.Organizer = object.replace("\"", "").trim();
                            }
                            if(predicate.contains("description")){
                                event.Description = object.replace("\"", "").replace("]","").trim();
                            }
                        lastsubject = subject;
                    }

                } finally {
                    statements.close();
                }

            } finally {
                model.close();
            }
        }
        return events;
    }
    public static List<Event> GetEventSainte(){
        List<Event> events = GetAllEvent();
        List<Event> eventsSainte = events.stream().filter(o -> o.Location != null && o.Location.equals("Saint-Etienne")).toList();
        return eventsSainte;
    }
    public static List<Event> GetEventSainteNotCourse(){
        List<Event> events = GetAllEvent();
        List<Event> eventsSainte = events.stream().filter(o -> o.Location != null && o.Location.equals("Saint-Etienne") && o.Categorie != null && !o.Categorie.equals("EducationEvent")).toList();
        return eventsSainte;
    }
    public static List<Event> GetEventNotInSainte(){
        List<Event> events = GetAllEvent();
        List<Event> eventsSainte = events.stream().filter(o ->o.Location != null && !o.Location.equals("Saint-Etienne")).toList();
        return eventsSainte;
    }
    public static List<Event> GetEventNotCourse(){
        List<Event> events = GetAllEvent();
        List<Event> eventsSainte = events.stream().filter(o ->o.Categorie != null && !o.Categorie.equals("EducationEvent")).toList();
        return eventsSainte;
    }
    public static List<Event> GetEventCourse(){
        List<Event> events = GetAllEvent();
        List<Event> eventsSainte = events.stream().filter(o ->o.Categorie != null && o.Categorie.equals("EducationEvent")).toList();
        return eventsSainte;
    }

    public static Event GetNextCourse(){

        LocalDateTime now = LocalDateTime.now();
        Event lesson = new Event();
        lesson.StartDate = now;
        List<Event> events = GetAllEvent();
        List<Event> lessons = events.stream().filter(e-> e.Categorie != null && e.Categorie.equals("EducationEvent")).toList();
        LocalDateTime bigger = now.plusDays(20);
        for (Event l:lessons){
            if(lesson.StartDate.isEqual(now))
            {
                lesson = l;
            }

            if(l.StartDate.isAfter(now)){
                if(l.StartDate.isBefore(bigger)){
                    bigger = l.StartDate;
                    lesson = l;
                }
            }
        }
        return lesson;
    }
    public static void Delete()
    {
     try (RDFConnection conn = RDFConnection.connectPW(
                         "https://territoire.emse.fr/ldp/maximeaurelien/",
                         "ldpuser",
                         "LinkedDataIsGreat"
                 )) {
                     conn.delete();
             }
    }

}
