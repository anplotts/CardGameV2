package com.ashley.CardGame.Services;

import com.ashley.CardGame.Models.Game;
import com.ashley.CardGame.Models.OhHell;
import com.ashley.CardGame.Models.Player;
import com.ashley.CardGame.Responses.JoinGameResponse;

import java.util.HashMap;


public class GameManagerService {
    public static GameManagerService instance = new GameManagerService();
    private final HashMap<String, GameService> gameServices = new HashMap<>();
    private final HashMap<String, OhHellService> ohHellServices = new HashMap<>();

    private GameManagerService() {
    }

    public JoinGameResponse createNewGame(String hostName, String gameName) {
        GameService game = null;
        Player player = null;

        switch (gameName) {
            case "Oh Hell":
                OhHellService ohHell = new OhHellService();
                game = ohHell;
                ohHellServices.put(game.getGameID(), ohHell);
                break;
            case "Other":
                break;
            default:
               throw new IllegalArgumentException("Invalid Game");
        }

        player = game.addPlayer(hostName, true);
        gameServices.put(game.getGameID(), game);


        return new JoinGameResponse(game.getGameID(), player.name, player.ID);
    }

    public JoinGameResponse joinGame(String playerName, String gameID) {
        GameService game = gameServices.get(gameID);
        Player player = game.addPlayer(playerName, false);

        return new JoinGameResponse(game.getGameID(), player.name, player.ID);
    }

    public GameService getGameService(String gameID) {
        return gameServices.get(gameID);
    }

    public OhHellService getOhHellService(String gameID) {
        return ohHellServices.get(gameID);
    }

    public void startGame(String gameID) {
        gameServices.get(gameID).startGame();
    }
}
