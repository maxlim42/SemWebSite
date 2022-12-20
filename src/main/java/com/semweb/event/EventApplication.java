package com.semweb.event;

import java.io.File;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.semweb.event.DAL.GetEventAlentoor;
import com.semweb.event.DAL.ParseIcsFile;
import com.semweb.event.Model.EventAlentoor;
import com.semweb.event.Traitement.Convert;
import com.semweb.event.Traitement.Send;

@SpringBootApplication
public class EventApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventApplication.class, args);
		ParseIcsFile cusr = new ParseIcsFile();
        File file = new File("Calendrier\\ADECal.ics");
        String calendar = "";
        try {
            calendar = cusr.getCalInfoFromIcs(file);
        } catch (Exception e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<EventAlentoor> eventsAlentoor =  GetEventAlentoor.GetData("https://www.alentoor.fr/saint-etienne/agenda");     
        Convert.CalendarToRDF(calendar +"\n");
        
        Send.TurtleFile();
	}

}
