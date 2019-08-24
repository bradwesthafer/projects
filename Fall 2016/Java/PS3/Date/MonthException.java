package Date;

/**
 * Created by Brad on 10/15/2016.
 */
public class MonthException extends Exception {
    public MonthException() {
        super("Invalid Month");
    }

    public MonthException(String message) {
        super(message);
    }
}
