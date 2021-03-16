package com.ashley.CardGame.Controllers;

import com.ashley.CardGame.Models.Game;
import com.ashley.CardGame.Responses.JoinGameResponse;
import com.ashley.CardGame.Responses.PlayerStatusResponse;
import com.ashley.CardGame.Services.GameManagerService;
import com.ashley.CardGame.Services.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")

        //(origins = "http://192.168.50.250:4200")

//gm = game management

public class GameManagerController {

    @PostMapping("/gm/createNewGame")
    public JoinGameResponse createNewGame(@RequestParam(value = "hostName", defaultValue = "Player") String hostName,
                                         @RequestParam(value = "selectedGame") String selectedGame) {
        JoinGameResponse response = GameManagerService.instance.createNewGame(hostName, selectedGame);
        return response;
    }

    @PutMapping("/gm/joinGame")
    public JoinGameResponse joinGame(@RequestParam(value = "playerName", defaultValue = "Player") String playerName,
                              @RequestParam(value = "gameID") String gameID) {
        JoinGameResponse response = GameManagerService.instance.joinGame(playerName, gameID);
        return response;
    }

    @GetMapping("/gm/playerStatus")
    public PlayerStatusResponse playerStatus(@RequestParam(value = "gameID") String gameID,
                                             @RequestParam(value = "playerID") String playerID) {
        GameService game = GameManagerService.instance.getGameService(gameID);
        return game.getPlayerStatus();
    }

    @PostMapping("/gm/startGame")
    public void startGame(@RequestParam(value = "gameID") String gameID) {
        GameManagerService.instance.startGame(gameID);
    }
}