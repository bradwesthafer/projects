package Rating;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Brad on 9/30/2016.
 */
public class RatingTest {
    public static void main(String[] args) throws IOException {
        Rating database = new Rating();
        try(BufferedReader file = new BufferedReader(new FileReader("ratings.txt"));) {
            int numFiles = Integer.valueOf(file.readLine());
            String movieName = file.readLine();
            int score;
            while (movieName != null) {
                score = Integer.valueOf(file.readLine());
                database.add(movieName, score);
                movieName = file.readLine();
            }
            database.printAll();
        } catch(FileNotFoundException e) {
            System.err.println("File Not Found " + e.getMessage());
        }
    }
}
