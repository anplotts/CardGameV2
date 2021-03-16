package com.ashley.CardGame.Models;

public class OhHellPlayer extends Player {
    public static final int NO_BID = -1;
    public int currentBid = NO_BID;
    public Card currentPlayedCard = null;
    public int currentTricksWon = 0;
    public int score = 0;

    public OhHellPlayer(String name, boolean isHost) {
        super(name, isHost);
    }


}
