package Movie;

/**
 * Created by Brad on 9/27/2016.
 */
public class Action extends Movie {
    protected final double lateFee = 3.00;

    public Action(MPAA rating, int id, String title) {
        super(rating, id, title);
    }

    //Override
    public double calculateLateFees(int daysLate) {
        return daysLate * lateFee;
    }
}
