package com.ashley.CardGame.Controllers;

import com.ashley.CardGame.Models.Game;
import com.ashley.CardGame.Responses.JoinGameResponse;
import com.ashley.CardGame.Services.GameManagerService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class GameManagerController {

    @PostMapping("/createNewGame")
    public JoinGameResponse createNewGame(@RequestParam(value = "hostName", defaultValue = "Player") String hostName,
                                         @RequestParam(value = "selectedGame") String selectedGame) {
        JoinGameResponse response = GameManagerService.instance.createNewGame(hostName, selectedGame);
        return response;
    }

    @PutMapping("/joinGame")
    public JoinGameResponse joinGame(@RequestParam(value = "playerName", defaultValue = "Player") String playerName,
                              @RequestParam(value = "gameID") String gameID) {
        JoinGameResponse response = GameManagerService.instance.joinGame(playerName, gameID);
        return response;
    }

    @GetMapping("/gameStatus")
    public Game gameStatus(@RequestParam(value = "gameID") String gameID) {
        Game game = GameManagerService.instance.getGame(gameID);
        return game;
    }
}