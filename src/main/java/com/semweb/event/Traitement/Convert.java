package com.semweb.event.Traitement;
import java.io.FileWriter;
import java.io.IOException;

import net.fortuna.ical4j.model.property.Organizer;
import org.apache.jena.datatypes.xsd.XSDDatatype.XSDGenericType;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

public class Convert {

    /**
     * Cette fonction permet de transformer un calendrier en fichiers RDF
     * @param calendar le calendrier ADECal.ics transformé en String
     */
    public static void CalendarToRDF(String calendar){

        String[] calendarSplit = calendar.split(", \\{");

        for (String cours : calendarSplit) {

            Model model = ModelFactory.createDefaultModel();
            model.setNsPrefix("rdf", RDF.uri);
            model.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");

            cours = cours.replace("[", "").replace("{","").replace("}", "").replace("]", "");
            System.out.println(cours);
            String[] coursSplit = cours.split(", description");
            String endDate = coursSplit[0].trim();
            String description = coursSplit[1].split(", location")[0].replace(endDate,"").replace("\n", " ").replace("=","");
            description = description.split("\\(")[0];
            String[] coursSplit2 = coursSplit[1].split(", event");
            String location = coursSplit2[0].split(" location")[1].replace("=", "");
            String organisateur = null;
            if(location.contains("Manufacture")){
                organisateur = "UJM";
            }
            if(location.contains("EMSE")){
                organisateur = "EMSE";
            }

            String eventName = cours.split(" event=")[1].split(", startDate")[0].trim().replace("=", "");
            String startDate = cours.split("startDate")[1].trim();

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
            ressource.addProperty(RDFS.label, eventName);
            if(organisateur == null)
            {
                organisateur = "EXAM";
            }
            ressource.addProperty(model.createProperty("https://schema.org/organizer"),organisateur);
            ressource.addProperty(model.createProperty("https://schema.org/description"),description);
            
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

        CreateEvent("Le plus grand cabaret du monde",  "TheaterEvent","2023-02-07T20:00:00", "2023-02-07T23:30:00", "Grenoble", "Non renseigné", "Le Plus Grand Cabaret Du Monde, le spectacle qui a conquis et émerveillé plus de 100.000 spectateurs");
        CreateEvent("Marche bio Albert Thomas",  "FoodEvent","2022-12-07T07:00:00", "2022-12-07T12:00:00", "Saint-Etienne", "Mairie de Saint-Etienne", "Marché de produits alimentaires.");
        CreateEvent("Soiree Big Band",  "MusicEvent","2022-12-16T20:30:00", "2022-12-16T23:30:00", "Saint-Etienne", "Mairie de Saint-Etienne", "Concert");
        CreateEvent("Les Chevaliers du Fiel - Travaux d'enfer",  "TheaterEvent","2023-02-25T20:00:00", "2023-02-25T23:30:00", "Chambery", "Non renseigné", "Les Chevaliers du Fiel nous embarquent dans une histoire encore plus dingue !");
    }

    /**
     * Cette fonction permet de créer un fichier RDF en entrant les informations de l'évènement
     * @param name nom de l'évènement
     * @param type le type d'évènement
     * @param startDate la date de début
     * @param endDate la date de fin
     * @param location le lieu
     * @param organisateur l'organisateur
     * @param description une description de l'évènement
     */
    public static void CreateEvent(String name, String type, String startDate, String endDate, String location, String organisateur, String description){
       
        Model model = ModelFactory.createDefaultModel();
            model.setNsPrefix("rdf", RDF.uri);
            model.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");

        Resource ressource = model.createResource("");
        ressource.addProperty(RDF.type, model.createProperty("https://schema.org/Event"));
        ressource.addProperty(model.createProperty("https://schema.org/additionalType"), model.createProperty("https://schema.org/" + type));
        ressource.addProperty(model.createProperty("http://www.w3.org/Event/startDate"), startDate,
                    XSDGenericType.XSDdateTime);
        ressource.addProperty(model.createProperty("http://www.w3.org/Event/endDate"), endDate,
                    XSDGenericType.XSDdateTime);
        ressource.addProperty(model.createProperty("https://schema.org/location"), location,
                    XSDGenericType.XSDstring);
        ressource.addProperty(RDFS.label, name);
        ressource.addProperty(model.createProperty("https://schema.org/organizer"),organisateur);
        ressource.addProperty(model.createProperty("https://schema.org/description"),description);

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
     * Cette fonction transforme une date sous forme "20221209T120441Z" en date sous forme "2022-12-09T12:04:41"
     * @param date une date du calendrier
     * @return String sous forme year-month-dayThour:min:sec
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
