import java.util.ArrayList;
import java.util.Scanner;

public class Airplane {
    private ArrayList<Boolean> seats = new ArrayList<Boolean>();

    Airplane() {
        // This implements the seating chart for 7 rows and 4 columns
        // Seats for row 1 are seats 0-3, row 2 are 4-7 and so on.
        for (int i = 0; i < 28; i++) {
            seats.add(false);
        }
    }

    void setSeat(int row, char col) throws IllegalArgumentException {
        if (row > 7) {
            throw new IllegalArgumentException("Row does not exist.");
        }

        int seat = (row * 4) - 4;
        if (col == 'a' || col == 'A') {}
        else if (col == 'b' || col == 'B') {
            seat += 1;
        }
        else if (col == 'c' || col == 'C') {
            seat += 2;
        }
        else if (col == 'd' || col == 'D') {
            seat += 3;
        }
        else {
            throw new IllegalArgumentException("Seat does not exist.");
        }

        if (seats.get(seat)) {
            throw new IllegalArgumentException("Seat already occupied");
        }
        else {
            seats.set(seat, true);
        }
    }

    void printGrid() {
        System.out.println(" ABCD");
        for (int i = 1; i < 8; i++) {
            System.out.print(i);
            int baseline = (i * 4) - 4;
            for (int j = 0; j < 4; j++) {
                if(seats.get(baseline + j)) {
                    System.out.print("X");
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}

class Main {
    public static void main(String[] args) {
        Airplane grid = new Airplane();
        //grid.printGrid();
        System.out.println("This program manages the seats on an airplane flight for a plane with 7 rows of 4 seats each.");
        System.out.println("The rows are numbered 1 through 7 and the seats within each row are called A, B, C and D.");
        boolean done = false;
        Scanner keyboard = new Scanner(System.in);
        while (!done) {
            System.out.print("Enter a row number or enter -1 to quit: ");
            int row = keyboard.nextInt();
            if (row == -1)
            {
                done = true;
            }
            else {
                System.out.print("Enter a seat from A to D: ");
                char col = keyboard.next().charAt(0);
                grid.setSeat(row, col);
                grid.printGrid();
            }
        }
    }
}