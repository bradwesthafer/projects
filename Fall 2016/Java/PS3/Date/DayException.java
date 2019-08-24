package Date;

/**
 * Created by Brad on 10/15/2016.
 */
public class DayException extends Exception {
    public DayException() {
        super("Invalid Day");
    }

    public DayException(String message) {
        super(message);
    }
}
