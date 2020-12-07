package com.ashley.CardGame.Services;

import com.ashley.CardGame.Models.Game;
import com.ashley.CardGame.Models.OhHell;
import com.ashley.CardGame.Models.OhHellPlayer;
import com.ashley.CardGame.Models.Player;
import com.ashley.CardGame.Utilities.Utility;

import java.util.HashMap;


public class GameManagerService {
    public static GameManagerService instance = new GameManagerService();
    private HashMap<String, Game> games = new HashMap<>();

    private GameManagerService() {
    }

    public Game createNewGame(String hostName, String gameName) {
        Game game = null;
        Player player = null;

        switch (gameName) {
            case "Oh Hell":
                game = new OhHell();
                break;
            case "Other":
                break;
            default:
               throw new IllegalArgumentException("Invalid Game");
        }

        game.addPlayer(hostName);
        games.put(game.ID, game);

        return game;
    }

    public Game joinGame(String playerName, String gameID) {
        Game game = games.get(gameID);
        game.addPlayer(playerName);

        return game;
    }

}
