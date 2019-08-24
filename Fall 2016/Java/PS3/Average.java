import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Brad on 10/14/2016.
 */
public class Average {
    public static void main(String[] args) {
        int n = 0;
        List<Integer> nums = new ArrayList<>();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("This program determines the average of a set of numbers.");
        try {
            System.out.print("How many numbers are in the set? ");
            n = keyboard.nextInt();
            if (n <= 0) {
                throw new IllegalArgumentException("N must be positive.");
            }
        } catch (IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
            n = 0;
        }
        // Forces the user to enter n numbers correctly, iff n was correctly entered.
        for (int i = 0; i < n; i++) {
            try {
                System.out.print("Enter the next number: ");
                nums.add(keyboard.nextInt());
            } catch (Exception e) {
                System.err.println("You must enter numbers using digits.");
                keyboard.next();
                i--;
            }
        }

        if (n != 0) {
            int sum = 0;
            for (int i: nums) {
                sum += i;
            }
            double average = sum / (double)n;
            System.out.println(String.format("The average of the %d numbers is: %.4f", n, average));
        }
    }
}
