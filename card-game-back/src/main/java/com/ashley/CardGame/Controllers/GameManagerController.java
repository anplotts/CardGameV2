package com.ashley.CardGame.Controllers;

import com.ashley.CardGame.Models.Game;
import com.ashley.CardGame.Models.NewGameResponse;
import com.ashley.CardGame.Services.GameManagerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameManagerController {

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/createNewGame")
    public NewGameResponse createNewGame(@RequestParam(value = "hostName", defaultValue = "Player") String hostName,
                                         @RequestParam(value = "selectedGame") String selectedGame) {
        Game game = GameManagerService.instance.createNewGame();
        NewGameResponse response = new NewGameResponse();
        response.gameID = game.ID;
        return response;
    }
}