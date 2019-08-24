package Movie;

/**
 * Created by Brad on 9/27/2016.
 */
public class Drama extends Movie {
    protected final double lateFee = 2.00;

    public Drama(MPAA rating, int id, String title) {
        super(rating, id, title);
    }

    //Override
    public double calculateLateFees(int daysLate) {
        return daysLate * lateFee;
    }
}
