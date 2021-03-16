package com.ashley.CardGame.Responses;
import com.ashley.CardGame.Models.Player;
import java.util.ArrayList;

public class PlayerStatusResponse<TPlayer extends Player> {
    public String gameID;
    public int maxNumPlayers;
    public int minNumPlayers;
    public ArrayList<TPlayer> players;
    public boolean isGameStarted;

    public PlayerStatusResponse(String gameID, int maxNumPlayers,
                                int minNumPlayers, ArrayList<TPlayer> players, boolean isGameStarted) {
        this.gameID = gameID;
        this.maxNumPlayers = maxNumPlayers;
        this.minNumPlayers = minNumPlayers;
        this.players = players;
        this.isGameStarted = isGameStarted;
    }
}
