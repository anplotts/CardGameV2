package com.ashley.CardGame.Models;

import com.ashley.CardGame.Utilities.Utility;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Player {
    public String name;
    public String ID;
    public boolean isHost;
    public boolean isConnected = true;

    @JsonIgnore
    public ArrayList<Card> hand = new ArrayList<>();

    public Player(String name, boolean isHost) {
        this.name = name;
        this.ID = Utility.generateID(3);
        this.isHost = isHost;
    }

}
