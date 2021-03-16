package com.ashley.CardGame.Models;

import com.ashley.CardGame.Utilities.Utility;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public abstract class Game<TPlayer extends Player> {
    public String ID;
    public boolean isGameStarted = false;
    public ArrayList<TPlayer> playersInTurnOrder = new ArrayList<>();
    public HashMap<String, TPlayer> players = new HashMap<>();

    public Game() {
        this.ID = Utility.generateID(5);
    }

    public abstract int getMaxNumPlayers();
    public abstract int getMinNumPlayers();
    public abstract int getPlayerCount();


}

