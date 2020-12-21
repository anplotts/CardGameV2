package com.ashley.CardGame.Models;

import com.ashley.CardGame.Utilities.Utility;

public class Player {
    private String name;
    private String ID;
    private boolean isHost;

    // Takes player's name as input to create a Player
    public Player(String name, boolean isHost) {
        this.name = name;
        this.ID = Utility.generateID(3);
        this.isHost = isHost;
    }

    public String getName() {
        return name;
    }

    public String getID() { return ID; }

    public boolean getIsHost() { return isHost; }
}
