package com.semweb.event.DAL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.semweb.event.Model.Event;


public class GetEventAlentoor {

    /**
     * Cette fonction permet de récupérer des évènements depuis un site web
     * @param url l'URL du site Alentoor
     * @return List<Event> la liste des évènements sur Alentoor
     */
    public static List<Event> GetData(String url) {
        List<Event> eventsAlentoor = new ArrayList<Event>();

        try {
            Document doc = Jsoup.connect(url).get();

            Element allEventHtml = doc.select("div.spotlights-items").first();
            Elements events = allEventHtml.select("div.item-spotlight");

            for (Element event : events) {
                Event eventAlentoor = new Event();

                Element category = event.select("div.categories").first();
                eventAlentoor.Categorie = category.text();

                Element location = event.select("div.location").first();
                eventAlentoor.Location = location.text();

                Element title = event.select("div.title").first();
                eventAlentoor.Name = title.text();

                Element dates = event.select("div.dates").first();
                eventAlentoor.Dates = dates.text();
//                System.out.println(dates.text());
                eventsAlentoor.add(eventAlentoor);
            }
            // item-spotlight
        } catch (Exception e) {
            System.out.println(e);
        }
        return eventsAlentoor;
    }
    
}

