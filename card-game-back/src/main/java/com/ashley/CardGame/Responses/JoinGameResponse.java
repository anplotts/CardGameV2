package com.ashley.CardGame.Responses;

public class JoinGameResponse {
    public String gameID;
    public String playerName;
    public String playerID;

    public JoinGameResponse(String gameID, String playerName, String playerID) {
        this.gameID = gameID;
        this.playerName = playerName;
        this.playerID = playerID;
    }
}
