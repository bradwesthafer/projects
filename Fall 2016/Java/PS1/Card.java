public class Card implements Comparable<Card> {
    private String suit;
    private String card;

    Card(String suit, String card) {
        this.suit = suit;
        this.card = card;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public boolean equals(Card c) {
        if (this.suit == c.getSuit() && this.card == c.getCard()) {
            return true;
        }
        else {
            return false;
        }
    }

    public int compareTo(Card c) {
        // This function defines comparisons of cards
        // If the suits are different, it returns the string comparison of the suits
        // If the cards are different, then it compares from least to greatest: Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King
        // If both the cards and the suits are the same, it returns a 0 for equal
        // If this is less than c, it returns a negative number. If this is greater than c, it returns a postive number.
        // It exists in order to allow Collections.sort() to work
        if (this.equals(c)) {
            return 0;
        }
        if (this.suit.equals(c.getSuit())) {
            if (this.card.equals("Ace")) {
                return -1;
            }
            else if (c.getCard().equals("Ace")) {
                return 1;
            }
            else if (this.card.equals("10")) {
                if (c.getCard().charAt(0) == 'J' || c.getCard().charAt(0) == 'K' || c.getCard().charAt(0) == 'Q') {
                    return -1;
                }
                else {
                    return 1;
                }
            }
            else if (c.getCard().equals("10")) {
                if (this.card.charAt(0) == 'J' || this.card.charAt(0) == 'K' || this.card.charAt(0) == 'Q') {
                    return 1;
                }
                else {
                    return -1;
                }
            }
            else if (this.card.equals("King")) {
                return 1;
            }
            else if (c.getCard().equals("King")) {
                return -1;
            }
            else {
                return this.card.compareTo(c.getCard());
            }
        }
        else {
            return this.suit.compareTo(c.getSuit());
        }
    }
}
