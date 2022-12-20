package com.semweb.event.Traitement;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Send {
    /**
     * 
     */
    public static void TurtleFile() {

        File folder = new File("Files");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if(file.isFile()){
                String fileName = file.getName().replace(".ttl", "");
                                String contentType = "\"Content-Type: text/turtle\"";
                String slug = "\"Slug: " + fileName + "\"";
                String calendarFile = "@Files/"+ file.getName(); 

                String[] command = {"curl", "-X", "POST", "-H", contentType, "-H", slug,"--data-binary", calendarFile, "https://territoire.emse.fr/ldp/maximeaurelien/", "-u", "ldpuser:LinkedDataIsGreat", "-v"};
                
                ProcessBuilder process = new ProcessBuilder(command);
                Process p;
                try {
                    p = process.start();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                        builder.append(System.getProperty("line.separator"));
                    }
                    String result = builder.toString();
                    System.out.print(result);
        
                } catch (IOException e) {
                    System.out.print("error");
                    e.printStackTrace();
                }
            }
        }                  
    }    
}

