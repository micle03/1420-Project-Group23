package FileReadersPackage;

import EventClassesPackage.Concert;
import EventClassesPackage.Event;
import EventClassesPackage.Seminar;
import EventClassesPackage.Workshop;
import MainPackage.FileReaderMain;

import java.io.*;

public class FileReaderEvents {

    public static void readEvents(String fileName) {
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(fileName));

            //reads the csv file until its null space
            while((line = reader.readLine()) != null) {

                //looks for comma separator
                String[] events = line.split(",");

                //adds each event detail to a new event
                String eventID = events[0];
                String title = events[1];
                String dateTime = events[2];
                String location = events[3];
                int capacity = Integer.parseInt(events[4]);
                String status = events[5];
                String eventType = events[6];
                String topic = events[7];
                String speakerName = events[8];
                int ageRestriction = Integer.parseInt(events[9]);

                //creates the new event
                Event newEvent = null;

                //finds which event type is being called
                if (eventType.equals("Workshop")) {
                    newEvent = new Workshop(eventID, title, dateTime, location, capacity, status, topic);
                } else if (eventType.equals("Seminar")) {
                    newEvent = new Seminar(eventID, title, dateTime, location, capacity, status, speakerName);
                } else if (eventType.equals("Concert")) {
                    newEvent = new Concert(eventID, title, dateTime, location, capacity, status, ageRestriction);
                }

                //creates new event
                FileReaderMain.systemEvents.add(newEvent);
            }

            //closes reader
            reader.close();
        } catch (Exception e) {
            //prints error exceptions if any
            System.out.println(e.getMessage());
        }
    }
}
