package com.group23.Users.model;

import java.util.Objects;

public abstract class User {

    private final String userId;
    private final String name;
    private final String email;

    protected User(String userId, String name, String email) {
        if (isBlank(userId)) throw new IllegalArgumentException("userId cannot be blank");
        if (isBlank(name)) throw new IllegalArgumentException("name cannot be blank");
        if (isBlank(email)) throw new IllegalArgumentException("email cannot be blank");

        this.userId = userId.trim();
        this.name = name.trim();
        this.email = email.trim();
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    //Just the Front and Back
    public abstract int getMaxConfirmedBookings();

    public abstract String getUserType();

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    @Override
    public String toString() {
        return String.format("%s[userId=%s, name=%s, email=%s]",
                getUserType(), userId, name, email);
    }

    // Helps with duplicates & words
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User other = (User) o;
        return userId.equals(other.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}