import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brad on 10/15/2016.
 */
public class RecursiveArray {
    public static void outputCombinations(String[][] data) {
        int[] iterations = new int[data.length];
        helper(data, iterations);
    }

    public static void helper(String[][] data, int[] iterations) {
        for(int i = 0; i < iterations.length; i++) {
            if(iterations[i] == data[i].length) {
                return;
            }
        }
        for(int i = 0; i < iterations.length; i++) {
            System.out.print(data[i][iterations[i]]);
            if(i != iterations.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.print("\n");
        for (int i = iterations.length-1; i >= 0; i--) {
            int[] iter = iterations.clone();
            iter[i]++;
            helper(data, iter);
            if (iterations[i] != 0) {
                return;
            }
        }
    }

    /*public static void main(String[] args) {
        String[][] data = {
                {"A", "B"},
                {"1", "2"},
                {"XX","YY","ZZ"}
                };
                outputCombinations(data);
        String[][] data2 = {
                {"A"},
                {"1"},
                {"2"},
                {"XX","YY"}
        };
        outputCombinations(data2);
        }*/
}
