import java.util.Scanner;

/**
 * Created by Brad on 10/15/2016.
 */
public class StringContains {
    public static boolean contains(String haystack, String needle) {
        if (haystack.isEmpty()) {
            return false;
        }
        else if (haystack.toUpperCase().startsWith(needle.toUpperCase())) {
            return true;
        }
        else {
            char[] temp = haystack.toCharArray();
            String newHaystack = "";
            for (int i = 1; i < temp.length; i++) {
                newHaystack += temp[i];
            }
            return contains(newHaystack, needle);
        }
    }

    public static void main(String[] args) {
        System.out.println("This program determines whether a string, called a needle, is contained within another string, called a haystack.");
        System.out.print("Enter the haystack: ");
        Scanner keyboard = new Scanner(System.in);
        String haystack = keyboard.nextLine();
        System.out.print("Enter the needle: ");
        String needle = keyboard.nextLine();
        contains(haystack, needle);
    }
}
