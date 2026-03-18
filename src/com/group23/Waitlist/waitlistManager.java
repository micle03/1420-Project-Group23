package com.group23.Waitlist;

import com.group23.Users.model.Guest;
import com.group23.Users.model.Staff;
import com.group23.Users.model.Student;
import com.group23.Users.model.User;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class waitlistManager {
    private final String directory = "src/com/group23/Waitlist/";
    public static final waitlistManager instance = new waitlistManager();
    public static waitlistManager getInstance() {
        return instance;
    }

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

    public void addToWaitlist(String eventId, User user) throws IOException {
        Path path = Paths.get(directory + eventId + "_Waitlist.txt");
        String id = user.getUserId();
        String userData = user.toCsvFormat();

        if (!Files.exists(path)) {
            createWaitlist(eventId);
        }

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

    public List<User> getWaitlistedUsers(String eventId) throws IOException {
        Path path = Paths.get(directory + eventId + "_Waitlist.txt");

        if (!Files.exists(path)) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }

        List<User> users = new ArrayList<>();
        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {
            String[] parts = line.split(", ");
            if (parts.length >= 4) {
                if(parts[3].equals("Staff")) {
                    Staff staff = new Staff(parts[0], parts[1], parts[2]);
                    users.add(staff);
                } else if(parts[3].equals("Student")) {
                    Student student = new Student(parts[0], parts[1], parts[2]);
                    users.add(student);
                } else if(parts[3].equals("Guest")) {
                    Guest guest = new Guest(parts[0], parts[1], parts[2]);
                    users.add(guest);
                }
            }
        }
        return users;
    }
    public Map<String, List<User>> getAllWaitlistedUsers() throws IOException {
        Map<String, List<User>> allWaitlistedUsers = new HashMap<>();
        Path dirPath = Paths.get(directory);

        if (!Files.exists(dirPath)) {
            return allWaitlistedUsers;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*_Waitlist.txt")) {
            for (Path entry : stream) {
                String fileName = entry.getFileName().toString();
                String eventId = fileName.replace("_Waitlist.txt", "");
                allWaitlistedUsers.put(eventId, getWaitlistedUsers(eventId));
            }
        }

        return allWaitlistedUsers;
    }
}
