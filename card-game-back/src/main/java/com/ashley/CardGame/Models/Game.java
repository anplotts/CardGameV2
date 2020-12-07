package com.ashley.CardGame.Models;

import com.ashley.CardGame.Utilities.Utility;
import java.util.HashMap;

public abstract class Game<T> {
    public String ID;
    protected HashMap<String, T> players = new HashMap<>();

    public Game() {
        this.ID = Utility.generateID(5);
    }

    public abstract void addPlayer(String playerName);
}
