
public class FileReaderMain {
    public static void main(String[] args) {

        //calls users reader class with this file name as a parameter
        System.out.println("-----Users-----");
        FileReaderUsers.readUsers("UsersTest.csv");
        System.out.println("-----Events-----");
        FileReaderEvents.readEvents("EventsTest.csv");
        System.out.println("-----Bookings------");
        FileReaderBookings.readBookings("BookingsTest.csv");
    }
}
