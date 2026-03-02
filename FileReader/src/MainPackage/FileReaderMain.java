package MainPackage;

import java.util.ArrayList;
import java.util.List;

import EventClassesPackage.Event;
import FileReadersPackage.FileReaderBookings;
import FileReadersPackage.FileReaderEvents;
import FileReadersPackage.FileReaderUsers;
import UserClassesPackage.User;
import BookingClassesPackage.Booking;

public class FileReaderMain {

    //global list of all the users, events, amd bookings in the system
    public static List<User> systemUsers = new ArrayList<>();
    public static List<Event> systemEvents = new ArrayList<>();
    public static List<Booking> systemBookings = new ArrayList<>();

    public static void main(String[] args) {

        //calls users reader class with the file name as a parameter
        FileReaderUsers.readUsers("UsersTest.csv");
        FileReaderEvents.readEvents("EventsTest.csv");
        FileReaderBookings.readBookings("BookingsTest.csv");

        //shows how the getters work
        System.out.println(systemUsers.get(1).getUserID());
        System.out.println(systemEvents.get(2).getStatus());
        System.out.println(systemBookings.get(3).getUserID());
    }
}
