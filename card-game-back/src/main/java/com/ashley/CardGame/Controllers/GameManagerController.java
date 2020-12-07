package com.ashley.CardGame.Controllers;

import com.ashley.CardGame.Models.Game;
import com.ashley.CardGame.Services.GameManagerService;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameManagerController {

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/createNewGame")
    public Game createNewGame(@RequestParam(value = "hostName", defaultValue = "Player") String hostName,
                                         @RequestParam(value = "selectedGame") String selectedGame) {
        Game game = GameManagerService.instance.createNewGame(hostName, selectedGame);
        return game;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/joinGame")
    public Game joinGame(@RequestParam(value = "playerName", defaultValue = "Player") String playerName,
                              @RequestParam(value = "gameID") String gameID) {
        Game game = GameManagerService.instance.joinGame(playerName, gameID);
        return game;
    }

}