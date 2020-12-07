package com.ashley.CardGame.Models;

import com.ashley.CardGame.Utilities.Utility;

public class Player {
    private String name;
    public String ID;

    // Takes player's name as input to create a Player
    public Player(String name) {
        this.name = name;
        this.ID = Utility.generateID(3);
    }


}
