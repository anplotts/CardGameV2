package com.ashley.CardGame.Services;

import com.ashley.CardGame.Models.Game;
import com.ashley.CardGame.Models.OhHell;
import com.ashley.CardGame.Models.OhHellPlayer;
import com.ashley.CardGame.Models.Player;
import com.ashley.CardGame.Responses.JoinGameResponse;
import com.ashley.CardGame.Utilities.Utility;

import java.util.HashMap;


public class GameManagerService {
    public static GameManagerService instance = new GameManagerService();
    private HashMap<String, Game> games = new HashMap<>();

    private GameManagerService() {
    }

    public JoinGameResponse createNewGame(String hostName, String gameName) {
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

        player = game.addPlayer(hostName, true);
        games.put(game.ID, game);


        return new JoinGameResponse(game.ID, player.getName(), player.getID());
    }

    public JoinGameResponse joinGame(String playerName, String gameID) {
        Game game = games.get(gameID);
        Player player = game.addPlayer(playerName, false);

        return new JoinGameResponse(game.ID, player.getName(), player.getID());
    }

    public Game getGame(String gameID) {
        return games.get(gameID);
    }

}
