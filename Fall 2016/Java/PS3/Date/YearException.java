package Date;

/**
 * Created by Brad on 10/15/2016.
 */
public class YearException extends Exception {
    public YearException() {
        super("Invalid Year");
    }

    public YearException(String message) {
        super(message);
    }
}
