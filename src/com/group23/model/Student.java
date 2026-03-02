package com.group23.model;

public class Student extends User {
    public Student(String userId, String name, String email) {
        super(userId, name, email);
    }

    @Override
    public int getMaxConfirmedBookings() {
        return 3;
    }

    @Override
    public String getUserType() {
        return "Student";
    }
}