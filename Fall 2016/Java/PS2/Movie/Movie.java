package Movie;

/**
 * Created by Brad on 9/27/2016.
 */
public class Movie {
    public enum MPAA{G, PG, PG13, R, NC17};
    private MPAA rating;
    private int id;
    private String title;
    protected final double lateFee = 2.00;

    // Constructor
    public Movie(MPAA rating, int id, String title) {
        this.rating = rating;
        this.id = id;
        this.title = title;
    }

    // Getters and Setters
    public MPAA getRating() {
        return rating;
    }

    public void setRating(MPAA rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //Override
    public boolean equals(Movie movie) {
        if (movie.getId() == this.id) {
            return true;
        }
        else {
            return false;
        }
    }

    public double calculateLateFees(int daysLate) {
        return daysLate * lateFee;
    }
}
