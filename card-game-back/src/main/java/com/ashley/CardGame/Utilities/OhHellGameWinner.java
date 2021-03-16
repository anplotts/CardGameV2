package com.ashley.CardGame.Utilities;

import com.ashley.CardGame.Models.OhHellPlayer;

import java.util.Comparator;

public class OhHellGameWinner implements Comparator<OhHellPlayer> {

    public int compare(OhHellPlayer player1, OhHellPlayer player2) {
        return Integer.compare(player1.score, player2.score);
    }
}
