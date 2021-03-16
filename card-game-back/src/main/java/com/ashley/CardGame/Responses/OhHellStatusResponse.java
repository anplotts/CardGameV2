package com.ashley.CardGame.Responses;

import com.ashley.CardGame.Models.Card;
import com.ashley.CardGame.Models.Player;

import java.util.ArrayList;

public class OhHellStatusResponse<TPlayer extends Player> {
    public ArrayList<TPlayer> players;
    public Card currentTrump;
    public ArrayList<Card> playerHand;
    public ArrayList<Card> playableCards;
    public boolean timeToBid = false;
    public boolean timeToPlay = false;
    public Player winner;
    public int invalidBid;
    public int maxBid;

    public OhHellStatusResponse(ArrayList<TPlayer> players,
                                Card currentTrump,
                                ArrayList<Card> playerHand,
                                boolean timeToBid,
                                boolean timeToPlay,
                                ArrayList<Card> playableCards,
                                Player winner,
                                int invalidBid,
                                int maxBid) {
        this.players = players;
        this.currentTrump = currentTrump;
        this.playerHand = playerHand;
        this.timeToBid = timeToBid;
        this.timeToPlay = timeToPlay;
        this.playableCards = playableCards;
        this.winner = winner;
        this.invalidBid = invalidBid;
        this.maxBid = maxBid;
    }
}
