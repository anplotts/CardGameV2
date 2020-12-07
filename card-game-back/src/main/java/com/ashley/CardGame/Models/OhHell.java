package com.ashley.CardGame.Models;

public class OhHell extends Game<OhHellPlayer> {

    @Override
    public void addPlayer(String playerName) {
        OhHellPlayer player = new OhHellPlayer(playerName);
        players.put(player.ID, player);
    }
}
