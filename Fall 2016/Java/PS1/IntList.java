import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class IntList {
    public static void main(String[] args) {
        int[] a = new int[50];
        ArrayList<NumList> freqLst = new ArrayList<NumList>();
        Scanner keyboard = new Scanner(System.in);
        int current = 0;
        int numNumbers = 0;
        System.out.println("This program produces frequency data for a list of up to 50 numbers.");
        while(current!= 99999 && numNumbers != 50) {
            System.out.print("Enter a number or enter 99999 to quit: ");
            current = keyboard.nextInt();
            if(current != 99999) {
                a[numNumbers++] = current;
            }
        }
        for (int i = 0; i < numNumbers; i++) {
            NumList temp = new NumList(a[i], 1);
            boolean exists = false;
            for(int j = 0; j < freqLst.size(); j++) {
                if(freqLst.get(j).equals(temp)) {
                    freqLst.set(j, new NumList(temp.getNumber(), (freqLst.get(j).getFrequency() + 1)));
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                freqLst.add(temp);
            }
        }

        Collections.sort(freqLst, Collections.reverseOrder());
        System.out.println("N      Count");
        for(NumList n: freqLst) {
            System.out.println(String.format("%-6d %d",n.getNumber(), n.getFrequency()));
        }
    }
}

class NumList implements Comparable<NumList> {
    private int number;
    private int frequency;

    public NumList(int number, int frequency) {
        this.number = number;
        this.frequency = frequency;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public boolean equals(NumList n) {
        if (this.number == n.number) {
            return true;
        }
        else {
            return false;
        }
    }

    public int compareTo(NumList n) {
        if (this.number > n.number) {
            return 1;
        }
        if (this.number == n.number) {
            return 0;
        }
        else {
            return -1;
        }
    }
}