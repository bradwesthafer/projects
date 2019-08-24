package Movie;

/**
 * Created by Brad on 9/27/2016.
 */
public class Comedy extends Movie {
    protected final double lateFee = 2.50;

    public Comedy(MPAA rating, int id, String title) {
        super(rating, id, title);
    }

    //Override
    public double calculateLateFees(int daysLate) {
        return daysLate * lateFee;
    }
}
