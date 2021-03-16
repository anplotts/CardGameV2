package com.ashley.CardGame.Utilities;

import com.ashley.CardGame.Models.Card;
import com.ashley.CardGame.Models.OhHellPlayer;

import java.util.Comparator;

public class OhHellHandWinner implements Comparator<OhHellPlayer> {
    String trumpSuit;
    String leadCardSuit;

    public OhHellHandWinner(String trumpSuit, String leadCardSuit) {
        this.trumpSuit = trumpSuit;
        this.leadCardSuit = leadCardSuit;
    }

    public int compare(OhHellPlayer player1, OhHellPlayer player2) {
        Card card1 = player1.currentPlayedCard;
        Card card2 = player2.currentPlayedCard;

        if (card1.suit.equals(card2.suit)) {
            return Integer.compare(card1.value, card2.value);
        }

        if (card1.suit.equals(trumpSuit)) {
            return 1;
        }

        if (card2.suit.equals(trumpSuit)) {
            return -1;
        }

        if (card1.suit.equals(leadCardSuit)) {
            return 1;
        }

        if (card2.suit.equals(leadCardSuit)) {
            return -1;
        }

        return 0;
    }
}