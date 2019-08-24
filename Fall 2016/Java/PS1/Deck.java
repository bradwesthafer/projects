import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Deck {
    private ArrayList<Card> d = new ArrayList<Card>();

    void printDeck() {
        for (Card c: d) {
            System.out.println(c.getCard() + " of " + c.getSuit());
        }
    }

    void sortDeck() {
        Collections.sort(d);
    }

    void add(String suit, String card) {
        d.add(new Card(suit, card));
    }

    String remove() throws ArrayIndexOutOfBoundsException {
        if (d.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Empty Deck");
        }
        Card temp = d.remove(0);
        return temp.getCard() + " of " + temp.getSuit();
    }

    void shuffle() {
        Random number = new Random();
        ArrayList<Card> temp = new ArrayList<Card>();
        while (d.size() > 0) {
            temp.add(d.remove(number.nextInt(d.size())));
        }
        d = temp;
    }
}

class Main {
    public static void main(String[] args) {
        Deck d = new Deck();
        boolean quit = false;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("This program manages a deck of cards\n");
        while(!quit) {
            int choice = 0;
            while (choice < 1 || choice > 6) {
                System.out.println("Your choices are:");
                System.out.println("1) Add a card to the deck");
                System.out.println("2) Remove the first card from the deck");
                System.out.println("3) Shuffle the deck");
                System.out.println("4) Sort the deck");
                System.out.println("5) Display the deck");
                System.out.println("6) Quit this program");
                System.out.print("Enter the number corresponding to your choice: ");
                choice = keyboard.nextInt();
                System.out.println();
            }
            if (choice == 1) {
                String suit, card;
                int s = 0, c = 0;
                while (s < 1 || s > 4) {
                    System.out.println("The 4 suits are:");
                    System.out.println("1) hearts");
                    System.out.println("2) clubs");
                    System.out.println("3) diamonds");
                    System.out.println("4) spades");
                    System.out.print("Enter the number corresponding to your choice of suit: ");
                    s = keyboard.nextInt();
                }
                if (s == 1) {
                    suit = "hearts";
                }
                else if (s == 2) {
                    suit = "clubs";
                }
                else if (s == 3) {
                    suit = "diamonds";
                }
                else {
                    suit = "spades";
                }
                while (c < 1 || c > 13) {
                    System.out.println("The 13 cards in a suit are:");
                    System.out.println("1) Ace");
                    System.out.println("2) 2");
                    System.out.println("3) 3");
                    System.out.println("4) 4");
                    System.out.println("5) 5");
                    System.out.println("6) 6");
                    System.out.println("7) 7");
                    System.out.println("8) 8");
                    System.out.println("9) 9");
                    System.out.println("10) 10");
                    System.out.println("11) J");
                    System.out.println("12) Q");
                    System.out.println("13) K");
                    System.out.print("Enter the number corresponding to your choice of card: ");
                    c = keyboard.nextInt();
                }
                if (c == 1) {
                    card = "Ace";
                }
                else if (c == 2) {
                    card = "2";
                }
                else if (c == 3) {
                    card = "3";
                }
                else if (c == 4) {
                    card = "4";
                }
                else if (c == 5) {
                    card = "5";
                }
                else if (c == 6) {
                    card = "6";
                }
                else if (c == 7) {
                    card = "7";
                }
                else if (c == 8) {
                    card = "8";
                }
                else if (c == 9) {
                    card = "9";
                }
                else if (c == 10) {
                    card = "10";
                }
                else if (c == 11) {
                    card = "Jack";
                }
                else if (c == 12) {
                    card = "Queen";
                }
                else {
                    card = "King";
                }
                d.add(suit, card);
                System.out.println(card + " of " + suit + " added to deck.\n");
            }
            else if (choice == 2) {
                System.out.println(d.remove() + " removed from deck.\n");
            }
            else if (choice == 3) {
                d.shuffle();
                System.out.println("Deck shuffled.\n");
            }
            else if (choice == 4) {
                d.sortDeck();
                System.out.println("Deck sorted.\n");
            }
            else if (choice == 5) {
                d.printDeck();
                System.out.println();
            }
            else {
                quit = true;
            }
        }
    }
}