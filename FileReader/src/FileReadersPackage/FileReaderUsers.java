package FileReadersPackage;

import java.io.*;

import MainPackage.FileReaderMain;
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
                String userID = users[0];
                String name = users[1];
                String email = users[2];
                String userType = users[3];

                //creates a new user
                User newUser = new User(userID, name, email, userType);

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