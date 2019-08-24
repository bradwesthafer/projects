import java.security.InvalidParameterException;
import java.util.Scanner;

/**
 * Created by Brad on 10/15/2016.
 */
public class Bowling {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("This program outputs the total number of bowling pins in a pyramid of n rows.");
        System.out.print("Enter n: ");
        int n = keyboard.nextInt();
        try {
            System.out.println(String.format("There are %d pins in a frame of %d rows", bowl(n), n));
        } catch (InvalidParameterException ipe) {
            System.err.println(ipe.getMessage());
        }
    }

    public static int bowl(int n) throws InvalidParameterException {
        if (n < 0) {
            throw new InvalidParameterException("n must be positive");
        }
        else if (n == 0 || n == 1) {
            return n;
        }
        else {
            return n + bowl(n - 1);
        }
    }
}
