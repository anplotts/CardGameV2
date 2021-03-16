package com.ashley.CardGame.Responses;

import com.ashley.CardGame.Models.Player;

import java.util.ArrayList;

public class InGamePlayerStatusResponse<TPlayer extends Player> {
    public ArrayList<InGamePlayerStatus> playerStatuses = new ArrayList<>();

    public InGamePlayerStatusResponse(ArrayList<TPlayer> players) {
        for (Player player : players) {
            InGamePlayerStatus playerStatus = new InGamePlayerStatus(player.ID, player.isConnected);
            playerStatuses.add(playerStatus);
        }
    }

    private class InGamePlayerStatus {
        public String playerID;
        public boolean isConnected;

        public InGamePlayerStatus(String playerID, boolean isConnected) {
            this.playerID = playerID;
            this.isConnected = isConnected;
        }
    }
}
