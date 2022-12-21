package com.semweb.event.Traitement;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.jena.datatypes.xsd.XSDDatatype.XSDGenericType;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

public class Convert {

    public static void CalendarToRDF(String calendar){

        String[] calendarSplit = calendar.split(", \\{");

        for (String cours : calendarSplit) {

            Model model = ModelFactory.createDefaultModel();
            model.setNsPrefix("rdf", RDF.uri);
            model.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");

            cours = cours.replace("[", "")
                         .replace("{","")
                         .replace("}", "")
                         .replace("]", "");

            String[] coursSplit = cours.split(", event");

            String endDate = coursSplit[0].trim();
            
            String[] coursSplit2 = coursSplit[1].split(", startDate");
            String eventName = coursSplit2[0].trim().replace("=", "");
            String startDate = coursSplit2[1].trim();

            endDate = ConvertDate(endDate);            
            startDate = ConvertDate(startDate);

            Resource ressource = model.createResource("");
            ressource.addProperty(RDF.type, model.createProperty("https://schema.org/Event"));
            ressource.addProperty(model.createProperty("https://schema.org/additionalType"), model.createProperty("https://schema.org/EducationEvent"));
            ressource.addProperty(model.createProperty("http://www.w3.org/Event/startDate"), startDate,
                    XSDGenericType.XSDdateTime);
            ressource.addProperty(model.createProperty("http://www.w3.org/Event/endDate"), endDate,
                    XSDGenericType.XSDdateTime);            
            ressource.addProperty(model.createProperty("https://schema.org/location"), "Saint-Etienne",
                    XSDGenericType.XSDstring);
            
            eventName = eventName.replaceAll("\\s+","");
            eventName = eventName.replaceAll(":", "-");
            eventName = eventName.replaceAll("'", "");
            eventName = eventName.replace("\\", "");
            eventName = eventName.replace("/","");

            String fileName = eventName + "-" + startDate.replaceAll(":", "-") + ".ttl";
            try { 
                FileWriter out = new FileWriter("src/main/java/com/semweb/event/Files/" + fileName.toLowerCase());
                model.write( out, "turtle" );
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }         
        }

        CreateEvent("Le plus grand cabaret du monde",  "TheaterEvent","2023-02-07T20:00:00", "2023-02-07T23:30:00", "Grenoble");
        CreateEvent("Marché bio Albert Thomas",  "FoodEvent","2022-12-07T07:00:00", "2022-12-07T12:00:00", "Saint-Etienne");
        CreateEvent("Soirée Big Band",  "MusicEvent","2022-12-16T20:30:00", "2022-12-16T23:30:00", "Saint-Etienne");
        CreateEvent("Les Chevaliers du Fiel - Travaux d'enfer",  "TheaterEvent","2023-02-25T20:00:00", "2023-02-25T23:30:00", "Chambery");
        
        // model.write(System.out, "turtle");
        
        // return model;
    }

    public static void CreateEvent(String name, String type, String startDate, String endDate, String location){
       
        Model model = ModelFactory.createDefaultModel();
            model.setNsPrefix("rdf", RDF.uri);
            model.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");

        Resource ressource = model.createResource("https://schema.org/Event/" + name + "/" + startDate);
        ressource.addProperty(RDF.type, model.createProperty("https://schema.org/Event"));
        ressource.addProperty(model.createProperty("https://schema.org/additionalType"), model.createProperty("https://schema.org/" + type));
        ressource.addProperty(model.createProperty("http://www.w3.org/Event/startDate"), startDate,
                    XSDGenericType.XSDdateTime);
        ressource.addProperty(model.createProperty("http://www.w3.org/Event/endDate"), endDate,
                    XSDGenericType.XSDdateTime);
        ressource.addProperty(model.createProperty("https://schema.org/location"), location,
                    XSDGenericType.XSDstring);
        name = name.replaceAll("\\s+","");
        name = name.replaceAll(":", "-");
        name = name.replaceAll("'", "");
        name = name.replaceAll("/", "");
        name = name.replace("\\TD", "TD");

        String fileName = name + "-" + startDate.replaceAll(":", "-") + ".ttl";
            try { 
                FileWriter out = new FileWriter("src/main/java/com/semweb/event/Files/" + fileName.toLowerCase());
                model.write( out, "turtle" );
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }      
    }
    
    /**
     * @param date
     * @return
     */
    public static String ConvertDate(String date)
    {
        if(date == null || date.isEmpty()){
            return "";
        }
        date = date.split("=")[1].replace("Z", "");
        // 20221209T120441Z
        String[] dateSplit = date.split("T");
        
        String year = dateSplit[0].substring(0,4);
        String month = dateSplit[0].substring(4, 6);
        String day = dateSplit[0].substring(6,8);
        
        String hour = dateSplit[1].substring(0,2);
        String min = dateSplit[1].substring(2,4);
        String sec = dateSplit[1].substring(4,6);
        String newDate =  year + "-" + month + "-" + day + "T" + hour + ":" + min + ":" + sec;
        
        return newDate;
    }
}
