package com.group23.FileReaders;

import com.group23.Events.*;
import com.group23.MainPackage.FileReaderMain;

import java.io.*;

public class FileReaderEvents {
    private static EventManager eventManager = EventManager.getInstance();

    public static void readEvents(String fileName) {
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(fileName));

            //reads the first line outside the while loop to skip the header titles
            reader.readLine();

            //reads the csv file until its null space
            while((line = reader.readLine()) != null) {

                //looks for comma separator
                String[] events = line.split(",", -1); //had to add a -1 that wont ignore empty spaces in the csv

                //adds each event detail to a new event and trims extra spaces
                String eventID = events[0].trim();
                String title = events[1].trim();
                String dateTime = events[2].trim();
                String location = events[3].trim();
                int capacity = Integer.parseInt(events[4].trim());
                String status = events[5].trim();
                String eventType = events[6].trim();


                //creates the new event
                Event newEvent = null;

                //finds which event type is being called
                if (eventType.equals("Workshop")) {
                    String topic = events[7].trim();
                    newEvent = new Workshop(eventID, title, dateTime, location, capacity, status, topic);
                } else if (eventType.equals("Seminar")) {
                    String speakerName = events[8].trim();
                    newEvent = new Seminar(eventID, title, dateTime, location, capacity, status, speakerName);
                } else if (eventType.equals("Concert")) {
                    String ageRestriction = events[9].trim();
                    newEvent = new Concert(eventID, title, dateTime, location, capacity, status, ageRestriction);
                }

                //creates new event
                FileReaderMain.systemEvents.add(newEvent);
                if(newEvent != null) eventManager.addEvent(newEvent);
            }

            //closes reader
            reader.close();
        } catch (Exception e) {
            //prints error exceptions if any
            System.out.println(e.getMessage());
        }
    }
}
