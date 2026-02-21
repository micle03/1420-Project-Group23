package MainPackage;

import java.util.ArrayList;
import java.util.List;

import EventClassesPackage.Event;
import FileReadersPackage.FileReaderBookings;
import FileReadersPackage.FileReaderEvents;
import FileReadersPackage.FileReaderUsers;
import UserClassesPackage.User;

public class FileReaderMain {

    //global list of all the users and events in the system
    public static List<User> systemUsers = new ArrayList<>();
    public static List<Event> systemEvents = new ArrayList<>();

    public static void main(String[] args) {

        //calls users reader class with the file name as a parameter
        FileReaderUsers.readUsers("UsersTest.csv");
        FileReaderEvents.readEvents("EventsTest.csv");
        FileReaderBookings.readBookings("BookingsTest.csv");

        //shows how the getters work
        System.out.println(systemUsers.get(1).getUserID());
        System.out.println(systemUsers.get(1).getName());
        System.out.println(systemUsers.get(1).getEmail());
    }
}
