package com.ashley.CardGame.Services;

import com.ashley.CardGame.Models.Game;

import java.util.HashMap;


public class GameManagerService {
    public static GameManagerService instance = new GameManagerService();
    private HashMap<String, Game> games = new HashMap<>();

    private GameManagerService() {
    }

    public Game createNewGame() {
        Game game = new Game();
        game.ID = getGameID();
        games.put(game.ID, game);
        return game;
    }

    private static String getGameID() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String gameID = "";

        for (int i = 0; i < 5; i++) {
            int index = (int) (alphabet.length() * Math.random());
            gameID += alphabet.charAt(index);
        }

        return gameID;
    }
}
