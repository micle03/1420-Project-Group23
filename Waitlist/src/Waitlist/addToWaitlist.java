package Waitlist;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.io.BufferedReader;



public class addToWaitlist {

    private Path path1;
    private Path path2;

    public addToWaitlist(Path path1, Path path2) {
        this.path1 = path1;
        this.path2 = path2;
    }

    public void checkSimilarity() throws Exception {
        Set<String> ids = new HashSet<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(this.path2.toFile()))){

            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                if (parts.length > 1){
                    ids.add(parts[1].trim());
                }
            }
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(this.path1.toFile()));
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.path2.toFile(), true))){

            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length > 1){
                    String id = parts[1].trim();

                    if (!ids.contains(id)){
                        writer.write(line + ", Waitlisted");
                        writer.newLine();

                        ids.add(id);
                    }
                    else{
                        System.out.println("User" + id + " is already in the waitlist");
                    }
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