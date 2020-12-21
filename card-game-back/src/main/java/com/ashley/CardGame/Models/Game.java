package com.ashley.CardGame.Models;

import com.ashley.CardGame.Utilities.Utility;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public abstract class Game<T extends Player> {
    public String ID;
    protected HashMap<String, T> players = new HashMap<>();

    public Game() {
        this.ID = Utility.generateID(5);
    }

    public abstract T addPlayer(String playerName, boolean isHost);
    public abstract int getMaxNumPlayers();
    public abstract int getMinNumPlayers();
    public abstract int getPlayerCount();

    @JsonIgnore
    public HashMap<String, T> getPlayers() {
        return this.players;
    }

    public ArrayList<T> getPlayersList() {
        Collection<T> values = players.values();
        return new ArrayList<T>(values);
    }
}

