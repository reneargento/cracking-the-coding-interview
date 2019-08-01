package chapter7_object_oriented_design;

import java.util.*;

/**
 * Created by Rene Argento on 28/07/19.
 */
public class Exercise1_DeckOfCards {

    public enum Suit {
        CLUB, DIAMOND, HEART, SPADE
    }

    public abstract class Card {
        protected String symbol;
        protected Suit suit;
        private boolean available;

        Card(Suit suit, String symbol) {
            this.suit = suit;
            this.symbol = symbol;
            available = true;
        }

        public abstract int value();

        public String symbol() {
            return symbol;
        }

        public Suit suit() {
            return suit;
        }

        public boolean isAvailable() {
            return available;
        }

        public void markUnavailable() {
            available = false;
        }

        public void markAvailable() {
            available = true;
        }
    }

    public class Hand<T extends Card> {
        protected List<T> cards;

        Hand() {
            cards = new ArrayList<>();
        }

        public int getPoints() {
            int points = 0;

            for (T card : cards) {
                points += card.value();
            }

            return points;
        }

        public void addCard(T card) {
            cards.add(card);
        }
    }

    @SuppressWarnings("unchecked")
    public class Deck<T extends Card> {
        private List<T> cards; // All cards, dealt or not
        private int dealtIndex; // Marks first undealt card

        public void setDeckOfCards(List<T> cards) {
            this.cards = cards;
        }

        public void shuffle() {
            T[] remainingCards = (T[]) new Object[remainingCards()];
            int index = 0;
            for (int i = dealtIndex; i < cards.size(); i++) {
                remainingCards[index++] = cards.get(i);
            }
            List<T> remainingCardsList = Arrays.asList(remainingCards);
            Collections.shuffle(remainingCardsList);

            index = 0;
            for (int i = dealtIndex; i < cards.size(); i++) {
                cards.set(i, remainingCardsList.get(index++));
            }
        }

        public int remainingCards() {
            return cards.size() - dealtIndex;
        }

        public T dealCard() {
            if (dealtIndex >= cards.size()) {
                throw new NoSuchElementException("Not enough cards");
            }

            T card = cards.get(dealtIndex);
            card.markUnavailable();
            dealtIndex++;
            return card;
        }

        public T[] dealHand(int handSize) {
            T[] hand = (T[]) new Object[handSize];
            for (int i = 0; i < handSize; i++) {
                hand[i] = dealCard();
            }
            return hand;
        }
    }

    // Blackjack
    public class BlackJackCard extends Card {
        BlackJackCard(Suit suit, String symbol) {
            super(suit, symbol);
        }

        @Override
        public int value() {
            if (isAce()) return 1; // Maximum value will be checked and updated in BlackJackHand
            if (isFaceValue()) return 10;
            return Integer.parseInt(symbol);
        }

        public boolean isAce() {
            return symbol.equals("A");
        }

        public boolean isFaceValue() {
            return symbol.equals("J")
                    || symbol.equals("Q")
                    || symbol.equals("K");
        }
    }

    public class BlackJackHand extends Hand<BlackJackCard> {
        @Override
        public int getPoints() {
            int points = 0;

            for (BlackJackCard card : cards) {
                if (card.isAce()) {
                    points += computeAcePoints();
                } else {
                    points += card.value();
                }
            }

            return points;
        }

        private int computeAcePoints() {
            boolean hasFaceValueCard = false;

            for (BlackJackCard card : cards) {
                if (card.isFaceValue()) {
                    hasFaceValueCard = true;
                    break;
                }
            }

            if (hasFaceValueCard) {
                return 11;
            } else {
                return 1;
            }
        }

        public boolean busted() {
            return getPoints() > 21;
        }

        public boolean is21() {
            return getPoints() == 21;
        }

        public boolean isBlackJack() {
            boolean hasAce = false;
            boolean hasFaceValueCard = false;

            if (cards.size() == 2) {
                BlackJackCard card1 = cards.get(0);
                BlackJackCard card2 = cards.get(1);

                if (card1.isAce() || card2.isAce()) {
                    hasAce = true;
                }
                if (card1.isFaceValue() || card2.isFaceValue()) {
                    hasFaceValueCard = true;
                }
            }

            return hasAce && hasFaceValueCard;
        }
    }

}
