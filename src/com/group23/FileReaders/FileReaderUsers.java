package com.group23.FileReaders;

import java.io.*;

import com.group23.MainPackage.FileReaderMain;
import com.group23.Users.service.UserManager;
import com.group23.Users.model.User;

public class FileReaderUsers {

    public static void readUsers(String fileName) {
        UserManager userManager = new UserManager();
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(fileName));

            //reads the first line outside the while loop to skip the header titles
            reader.readLine();

            //reads the csv file until its null space
            while((line = reader.readLine()) != null) {

                //looks for comma separator
                String[] users = line.split(",");

                //gets the users data from the csv and put it into its own temp string and trims extra spaces
                String userID = users[0].trim();
                String name = users[1].trim();
                String email = users[2].trim();
                String userType = users[3].trim();

                //creates a new user
                User newUser = userManager.createUser(userID, name, email, userType);

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