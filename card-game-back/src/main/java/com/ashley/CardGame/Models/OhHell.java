package com.ashley.CardGame.Models;

public class OhHell extends Game<OhHellPlayer> {

    @Override
    public OhHellPlayer addPlayer(String playerName, boolean isHost) {
        OhHellPlayer player = new OhHellPlayer(playerName, isHost);
        players.put(player.getID(), player);
        return player;
    }

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
