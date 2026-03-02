package com.group23.FileReaders;

import com.group23.MainPackage.FileReaderMain;
import com.group23.Bookings.Booking;

import java.io.*;

public class FileReaderBookings {

    public static void readBookings(String fileName) {
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(fileName));

            //reads the first line outside the while loop to skip the header titles
            reader.readLine();

            //reads the csv file until its null space
            while((line = reader.readLine()) != null) {

                //looks for comma separator
                String[] bookings = line.split(",");

                //gets the booking data from the csv file and trims extra spaces
                String bookingID = bookings[0].trim();
                String userID = bookings[1].trim();
                String eventID = bookings[2].trim();
                String createdAt = bookings[3].trim();
                String bookingStatus = bookings[4].trim();

                //creates a new booking
                Booking newBooking = new Booking(bookingID, userID, eventID, createdAt, bookingStatus);

                //adds the new booking to the global bookings list
                FileReaderMain.systemBookings.add(newBooking);
            }

            //closes reader
            reader.close();
        } catch (Exception e) {
            //prints error exceptions if any
            System.out.println(e.getMessage());
        }
    }
}
