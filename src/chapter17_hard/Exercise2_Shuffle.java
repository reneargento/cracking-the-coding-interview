package chapter17_hard;

import java.util.Random;

/**
 * Created by Rene Argento on 26/04/20.
 */
public class Exercise2_Shuffle {

    private enum Suit {
        SPADES, HEARTS, DIAMONDS, CLUBS
    }

    private static class Card {
        String value;
        Suit suit;

        Card(String value, Suit suit) {
            this.value = value;
            this.suit = suit;
        }

        @Override
        public String toString() {
            return value + " " + suit;
        }
    }

    // O(n) runtime, where n is the number of cards in the deck
    // O(1) space
    public static void shuffle(Card[] cards) {
        Random random = new Random();

        for (int i = 0; i < cards.length; i++) {
            // Choose a random card between the current index and the end of the deck
            int randomIndex = i + random.nextInt(cards.length - i);

            // Swap cards
            Card randomCard = cards[randomIndex];
            cards[randomIndex] = cards[i];
            cards[i] = randomCard;
        }
    }

    public static void main(String[] args) {
        Card[] deck = getDeck();
        shuffle(deck);

        System.out.println("Shuffled deck");
        for (Card card : deck) {
            System.out.println(card);
        }
    }

    private static Card[] getDeck() {
        Card[] deck = new Card[52];

        String[] values = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        Suit[] suits = { Suit.SPADES , Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS };

        int deckIndex = 0;
        for (Suit suit : suits) {
            for (String value : values) {
                deck[deckIndex++] = new Card(value, suit);
            }
        }
        return deck;
    }

}
