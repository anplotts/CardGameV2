package com.ashley.CardGame.Utilities;

import com.ashley.CardGame.Models.Card;
import java.util.Comparator;

public class HandSorter implements Comparator<Card> {

    public static HandSorter instance = new HandSorter();

    public int compare(Card card1, Card card2) {
        if (card1.suit.equals(card2.suit)) {
            return Integer.compare(card1.value, card2.value);
        }

        if (card1.suit.equals(Card.HEARTS) || card2.suit.equals(Card.CLUBS)) {
            return 1;
        }

        if (card1.suit.equals(Card.CLUBS) || card2.suit.equals(Card.HEARTS)) {
            return -1;
        }

        if (card1.suit.equals(Card.DIAMONDS)) {
            return -1;
        }

        return 1;
    }
}
