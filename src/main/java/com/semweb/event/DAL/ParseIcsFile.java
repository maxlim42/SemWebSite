package com.semweb.event.DAL;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Version;


public class ParseIcsFile {

 /**
  * Cette fonction permet de lire et de transformer un fichier ics en texte
  * @param file le fichier ics
  * @return String le calendrier tiré du ics
  * @throws Exception
  */
 public String getCalInfoFromIcs(File file) throws Exception {

  net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
  calendar.getProperties().add(Version.VERSION_2_0);
  calendar.getProperties().add(CalScale.GREGORIAN);

  FileInputStream fin = new FileInputStream(file);
  CalendarBuilder builder = new CalendarBuilder();
  calendar = builder.build(fin);
  List result = new ArrayList<String>();

  for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
   Map<String, String> calenderinfo = new HashMap<String, String>();
   Component component = (Component) i.next();

   for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
    try {
     String startdate = null, enddate = null, event = null, description = null, location = null;
     Property property = (Property) j.next();
     if ("DTSTART".equals(property.getName())) {
      startdate = property.getValue();
      calenderinfo.put("startDate",
        startdate);
     }
     if ("DTEND".equals(property.getName())) {
      enddate = property.getValue();
      calenderinfo.put("endDate",
        enddate);
     }
     if ("SUMMARY".equals(property.getName())) {
      event = property.getValue();
      calenderinfo.put("event", event);
     }

     if ("DESCRIPTION".equals(property.getName())) {
      description = property.getValue();
      calenderinfo.put("description", description);
     }
     if ("LOCATION".equals(property.getName())) {
      location = property.getValue();
      calenderinfo.put("location", location);
     }
     if (!calenderinfo.isEmpty()) {
      if (calenderinfo.get("event") != null) {
       result.add(calenderinfo);
      }
     }

    } catch (Exception ex) {
      System.out.println(ex);
    }
   }
  }
  return result.toString();
 }
}
