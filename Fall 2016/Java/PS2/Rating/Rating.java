package Rating;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brad on 9/30/2016.
 */
public class Rating {
    // Instance Data
    private Map<String, Integer> numReviewMap = new HashMap<>();
    private Map<String, Integer> sumScoreMap = new HashMap<>();

    // Methods
    public void add(String movieName, int score) {
        if (numReviewMap.containsKey(movieName)) {
            numReviewMap.put(movieName, numReviewMap.get(movieName) + 1);
            sumScoreMap.put(movieName, sumScoreMap.get(movieName) + score);
        }
        else {
            numReviewMap.put(movieName, 1);
            sumScoreMap.put(movieName, score);
        }
    }

    public double calculateAverage(String movieName) throws NullPointerException {
        if (numReviewMap.containsKey(movieName)) {
            return (double)(sumScoreMap.get(movieName)) / (double)(numReviewMap.get(movieName));
        }
        else {
            throw new NullPointerException(movieName + " is not in the database.");
        }
    }

    public void printAll() {
        for (String movie: numReviewMap.keySet()) {
            if (numReviewMap.get(movie) == 1) {
                System.out.println(String.format("%s: %d review, average of %d / 5", movie, numReviewMap.get(movie), sumScoreMap.get(movie)));
            }
            else {
                System.out.println(String.format("%s: %d reviews, average of %.1f / 5", movie, numReviewMap.get(movie), calculateAverage(movie)));
            }
        }
    }
}
