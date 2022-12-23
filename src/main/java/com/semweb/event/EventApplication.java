package com.semweb.event;

import java.io.File;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.semweb.event.Controlleur.ControlleurPage;
import com.semweb.event.DAL.GetEventAlentoor;
import com.semweb.event.DAL.GetRDF;
import com.semweb.event.DAL.ParseIcsFile;
import com.semweb.event.Model.Event;
import com.semweb.event.Traitement.Convert;
import com.semweb.event.Traitement.Send;

@SpringBootApplication
@ComponentScan(basePackageClasses=ControlleurPage.class)
public class EventApplication {

    public static Boolean SendRDF = false;
	public static void main(String[] args) {
		SpringApplication.run(EventApplication.class, args);
        
        if(SendRDF){
            ParseIcsFile cusr = new ParseIcsFile();
            File file = new File("src\\main\\java\\com\\semweb\\event\\Calendrier\\ADECal.ics");
            String calendar = "";
            try {
                calendar = cusr.getCalInfoFromIcs(file);
            } catch (Exception e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }
            // List<Event> events =  GetEventAlentoor.GetData("https://www.alentoor.fr/saint-etienne/agenda");     
            Convert.CalendarToRDF(calendar +"\n");
            
            Send.TurtleFile();
        }	
        GetRDF.GetAllEvent();
	}

}
