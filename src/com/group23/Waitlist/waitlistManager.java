package com.group23.Waitlist;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class waitlistManager {
    private final String directory = "src/com/group23/Waitlist/src/";

    public void createWaitlist(String eventId) {
        File file = new File(directory + eventId + "_Waitlist.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("New waitlist created: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("Error creating waitlist file.");
        }
    }

    public void addToWaitlist(String eventId, String userData) throws IOException {
        Path path = Paths.get(directory + eventId + "_Waitlist.txt");
        String id = userData.split(",")[0].trim();// Splits the user data from each instance of a "," and extracts the first users id

        List<String> lines = Files.readAllLines(path);


        for (String line : lines) {
            if (line.contains(id)) {
                System.out.println("User " + id + " is already on this waitlist.");
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write(userData + ", Waitlisted");// Adds waitlisted to end of the users line
            writer.newLine();
        }
    }

    public String promoteUser(String eventId) throws IOException {
        Path path = Paths.get(directory + eventId + "_Waitlist.txt");
        List<String> lines = Files.readAllLines(path);

        if (lines.isEmpty()) {
            return null; // Returns that there was no one in the waitlist
        }

        String promotedUser = lines.removeFirst();

        Files.write(path, lines);

        return promotedUser.replace("Waitlisted", "Confirmed");
    }

    public void removeFromWaitlist(String eventId, String userId) throws IOException {
        Path path = Paths.get(directory + eventId + "_Waitlist.txt");
        List<String> lines = Files.readAllLines(path);

        lines.removeIf(line -> line.contains(userId));

        Files.write(path, lines);
    }

    public void clearWaitlist(String eventId) throws IOException {
        Path path = Paths.get(directory + eventId + "_Waitlist.txt");
        Files.write(path, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);// Creates new file with zero bytes or makes the existing file zero bytes
    }
}
