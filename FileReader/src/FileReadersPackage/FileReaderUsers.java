package FileReadersPackage;

import java.io.*;

import MainPackage.FileReaderMain;
import UserClassesPackage.Guest;
import UserClassesPackage.Staff;
import UserClassesPackage.Student;
import UserClassesPackage.User;

public class FileReaderUsers {

    public static void readUsers(String fileName) {
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(fileName));

            //reads the csv file until its null space
            while((line = reader.readLine()) != null) {

                //looks for comma separator
                String[] users = line.split(",");

                //gets the users data from the csv and put it into its own temp string
                String userId = users[0];
                String name = users[1];
                String email = users[2];
                String userType = users[3];

                //creates a new user
                User newUser = null;

                //goes through each type of user to send to the correct subclass
                if (userType.equals("Student")) {
                    newUser = new Student(userId, name, email);
                } else if (userType.equals("Staff")) {
                    newUser = new Staff(userId, name, email);
                } else if (userType.equals("Guest")) {
                    newUser = new Guest(userId, name, email);
                }

                //adds the new user to the global list
                FileReaderMain.systemUsers.add(newUser);
            }

            //closes reader
            reader.close();
        } catch (Exception e) {
            //prints error exceptions if any
            System.out.println(e.getMessage());
        }
    }
}