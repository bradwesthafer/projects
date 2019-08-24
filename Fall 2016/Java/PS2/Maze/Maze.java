package Maze;

import java.util.Scanner;

/**
 * Created by Brad on 9/30/2016.
 */
public class Maze {
    public static void main(String[] args) {
        // Create the Maze
        Node current = new Node('A', null, null, null, null);
        current.setSouth(new Node('E', current, null, null, null));
        current.getSouth().setSouth(new Node('I', current.getSouth(), null, null, null));
        current.getSouth().getSouth().setEast(new Node('J', null, null, null, current.getSouth().getSouth()));
        current.setEast(new Node('B', null, null, null, current));
        current.getEast().setSouth(new Node('F', current.getEast(), null, null, null));
        current.getEast().getSouth().setEast(new Node('G', null, null, null, current.getEast().getSouth()));
        current.getEast().getSouth().getEast().setNorth(new Node('C', null, current.getEast().getSouth().getEast(), null, null));
        current.getEast().getSouth().getEast().getNorth().setEast(new Node('D', null, null, null, current.getEast().getSouth().getEast().getNorth()));
        current.getEast().getSouth().getEast().setSouth(new Node('K', current.getEast().getSouth().getEast(), null, null, null));
        current.getEast().getSouth().getEast().setEast(new Node('H', null, null, null, current.getEast().getSouth().getEast()));
        current.getEast().getSouth().getEast().getEast().setSouth(new Node('L', current.getEast().getSouth().getEast().getEast(), null, null, null));

        Scanner keyboard = new Scanner(System.in);

        while(current.getData() != 'L') {
            // Determine Current Moves
            boolean hasNorth = false;
            boolean hasSouth = false;
            boolean hasEast = false;
            boolean hasWest = false;
            String directions = "";
            if(current.getNorth() != null) {
                directions = "north";
                hasNorth = true;
            }
            if(current.getEast() != null) {
                if(directions == "") {
                    directions = "east";
                }
                else {
                    directions += " or east";
                }
                hasEast = true;
            }
            if(current.getWest() != null) {
                if(directions == "") {
                    directions = "west";
                }
                else {
                    directions += " or west";
                }
                hasWest = true;
            }
            if(current.getSouth() != null) {
                if(directions == "") {
                    directions = "south";
                }
                else {
                    directions += " or south";
                }
                hasSouth = true;
            }
            // Get Input
            System.out.println(String.format("You are in room %c of a maze of twisty little passages, all alike. You can go %s.", current.getData(), directions));
            char choice = keyboard.nextLine().toUpperCase().charAt(0);
            // Input Verification
            while ((choice != 'N' && choice != 'S' && choice != 'W' && choice != 'E') || (choice == 'N' && !hasNorth) ||
                    (choice == 'S' && !hasSouth) || (choice == 'W' && !hasWest) || (choice == 'E' && !hasEast)) {
                System.out.println("Invalid choice.");
                System.out.println(String.format("You are in room %c of a maze of twisty little passages, all alike. You can go %s.", current.getData(), directions));
                choice = keyboard.nextLine().toUpperCase().charAt(0);
            }
            // Move to next node
            if (choice == 'N') {
                current = current.getNorth();
            }
            else if (choice == 'S') {
                current = current.getSouth();
            }
            else if (choice == 'W') {
                current = current.getWest();
            }
            else {
                current = current.getEast();
            }
        }
        System.out.println(String.format("You are in room %c of a maze of twisty little passages, all alike. You have reached the exit of the maze. Congratulations!", current.getData()));
    }
}
