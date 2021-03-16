package com.ashley.CardGame.Models;

public class OhHell extends Game<OhHellPlayer> {
    public int firstBidder = 0;
    public int firstPlayer = 0;
    public int roundNum = 1;
    public Card currentTrump = null;
    public String leadSuit = "";
    public boolean isTrumpBroken = false;
    public OhHellPlayer winner = null;

    @Override
    public int getMaxNumPlayers() {
        return 5;
    }

    @Override
    public int getMinNumPlayers() {
        return 2;
    }

    @Override
    public int getPlayerCount() {
        return players.size();
    }
}
