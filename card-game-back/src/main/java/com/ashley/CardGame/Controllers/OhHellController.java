package com.ashley.CardGame.Controllers;

import com.ashley.CardGame.Models.Card;
import com.ashley.CardGame.Responses.OhHellStatusResponse;
import com.ashley.CardGame.Responses.InGamePlayerStatusResponse;
import com.ashley.CardGame.Services.GameManagerService;
import com.ashley.CardGame.Services.OhHellService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")

        //(origins = "http://192.168.50.250:4200")

// oh = oh hell

public class OhHellController {

    @GetMapping("/oh/gameStatus")
    public OhHellStatusResponse gameStatus(@RequestParam(value = "gameID") String gameID,
                                           @RequestParam(value = "playerID") String playerID) {
        OhHellService game = GameManagerService.instance.getOhHellService(gameID);
        return game.getGameStatus(playerID);
    }

    @GetMapping("/oh/playerStatus")
    public InGamePlayerStatusResponse playerStatus(@RequestParam(value = "gameID") String gameID,
                                                 @RequestParam(value = "playerID") String playerID) {
        OhHellService game = GameManagerService.instance.getOhHellService(gameID);
        return game.getInGamePlayerStatus();
    }

    @PostMapping("/oh/submitBid")
    public OhHellStatusResponse submitBid(@RequestParam(value = "gameID") String gameID,
                                          @RequestParam(value = "playerID") String playerID,
                                          @RequestParam(value = "bid") int bid) {
        OhHellService game = GameManagerService.instance.getOhHellService(gameID);
        game.submitBid(playerID, bid);
        return game.getGameStatus(playerID);
    }

    @PostMapping("/oh/playCard")
    public OhHellStatusResponse playCard(@RequestParam(value = "gameID") String gameID,
                                          @RequestParam(value = "playerID") String playerID,
                                          @RequestParam(value = "suit") String suit,
                                         @RequestParam(value = "value") int value) {
        OhHellService game = GameManagerService.instance.getOhHellService(gameID);
        Card card = new Card(value, suit);
        game.playCard(playerID, card);
        game.updateGameState();
        return game.getGameStatus(playerID);
    }
}