package Waitlist;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class waitlistManager {
    private final String directory = "Waitlist/src/";

    public void createWaitlist(String eventId) {
        File file = new File(directory + eventId + "_Waitlist.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("New waitlist created: " + file.getName());
            }
        } catch (IOException e) {
            System.err.println("Error creating waitlist file.");
        }
    }


    public void addToWaitlist(String eventId, String userData) throws IOException {
        Path path = Paths.get(directory + eventId + "_Waitlist.txt");
        String ids = userData.split(",")[1].trim();

        List<String> lines = Files.readAllLines(path);


        for (String line : lines) {
            if (line.contains(ids)) {
                System.out.println("User " + ids + " is already on this waitlist.");
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true))) {
            writer.write(userData + ", Waitlisted");
            writer.newLine();
        }
    }

    public String promoteUser(String eventId) throws IOException {
        Path path = Paths.get(directory + eventId + "_Waitlist.txt");
        List<String> lines = Files.readAllLines(path);

        if (lines.isEmpty()) {
            return null;
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
        Files.write(path, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
    }
}
