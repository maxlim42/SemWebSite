package com.semweb.event.DAL;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;


public class ParseIcsFile {

 public String getCalInfoFromIcs(File file) throws Exception {

  net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
  calendar.getProperties().add(
    new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
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
     String startdate = null, enddate = null, event = null;
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

     if (!calenderinfo.isEmpty()) {
      if (calenderinfo.get("event") != null) {
       result.add(calenderinfo);
      }
     }

    } catch (Exception ex) {

    }
   }
  }
  return result.toString();
 }
}
