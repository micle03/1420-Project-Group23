package com.group23.service;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.group23.model.User;
import com.group23.model.Student;
import com.group23.model.Staff;
import com.group23.model.Guest;

public class UserManager {
    //String must be a key, user a value->Create unique map
    private final Map<String, User> usersById = new HashMap<>();
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
}