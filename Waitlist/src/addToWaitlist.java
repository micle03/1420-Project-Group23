import java.io.BufferedReader;
import java.nio.file.*;
import java.util.*;

public class addToWaitlist {

    private Path path1;
    private Path path2;

    public addToWaitlist(Path path1, Path path2) {
        this.path1 = path1;
        this.path2 = path2;
    }

    public void checkSimilarity() throws Exception {
        Set<String> ids = new HashSet<>();


        try (BufferedReader reader = Files.newBufferedReader(this.path1)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    ids.add(parts[1].trim());
                }
            }
        }

        try (BufferedReader reader = Files.newBufferedReader(this.path2)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1 && ids.contains(parts[1].trim())) {
                    System.out.println("This user is already in the waitlist: " + line);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Path path1 = Paths.get("Waitlist/src/users.txt");
        Path path2 = Paths.get("Waitlist/src/Waitlist.txt");

        addToWaitlist add = new addToWaitlist(path1, path2);
        add.checkSimilarity();
    }
}