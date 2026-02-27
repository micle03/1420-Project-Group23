package Waitlist;

import java.io.File;
import java.io.IOException;

public class createWaitlist { // Class names should be PascalCase

    public String eventName;

    public createWaitlist(String eventName) {
        this.eventName = eventName;
    }

    public void createFile() {
        String fileName = eventName + "_Waitlist.txt";
        // Ensure the "Waitlist/src/" directory exists, or this will throw an IOException
        File file = new File("Waitlist/src/" + fileName);

        if (file.exists()) {
            System.out.println("File name already exists...loading file");
        } else {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    // The main method must be public static void main(String[] args)
    public static void main(String[] args) {
        String eventName = "COOPsession";
        createWaitlist waitlist = new createWaitlist(eventName);
        waitlist.createFile();
    }
}