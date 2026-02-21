import java.io.*;

public class FileReaderEvents {

    public static void readEvents(String fileName) {
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(fileName));

            //reads the csv file until its null space
            while((line = reader.readLine()) != null) {

                //looks for comma separator
                String[] events = line.split(",");

                //prints the csv file
                for(String index : events) {
                    System.out.println(index);
                }
            }

            //closes reader
            reader.close();
        } catch (Exception e) {
            //prints error exceptions if any
            System.out.println(e.getMessage());
        }
    }
}
