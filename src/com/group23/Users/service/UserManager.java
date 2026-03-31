package com.group23.Users.service;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

import com.group23.Bookings.BookingService;
import com.group23.Users.model.User;
import com.group23.Users.model.Student;
import com.group23.Users.model.Staff;
import com.group23.Users.model.Guest;

public class UserManager {
    //String must be a key, user a value->Create unique map
    private final Map<String, User> usersById = new HashMap<>();

    private static final UserManager instance = new UserManager();
    public static UserManager getInstance() {
        return instance;
    }
    BookingService bookingService = BookingService.getInstance();

    //Creates subclass based on user type
    public User createUser(String userId, String name, String email, String userType) {

        if (usersById.containsKey(userId)) {
            throw new IllegalArgumentException("User ID already exists: " + userId);
        }

        User user;

        switch (userType.toLowerCase()) {
            case "student":
                user = new Student(userId, name, email);
                break;
            case "staff":
                user = new Staff(userId, name, email);
                break;
            case "guest":
                user = new Guest(userId, name, email);
                break;
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }
        //Stores inside HashMap
        usersById.put(userId, user);
        bookingService.registerUser(userId, userType);

        //saves the user to the file
        saveUsersToFile();

        return user;
    }

    public User getUserById(String userId) {
        return usersById.get(userId);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(usersById.values());
    }

    public int getTotalUsers() {
        return usersById.size();
    }

    //file writer
    public void saveUsersToFile() {
        //creates a writer and tries to write to the users.csv
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter("users.csv"))) {
            writer.println("userID,name,email,userType"); // Header of the csv
            for (User user : usersById.values()) {
                writer.println(user.toCsvFormat());
            }
        } catch (java.io.IOException e) {
            System.out.println("Error saving something in users: " + e.getMessage());
        }
    }
}