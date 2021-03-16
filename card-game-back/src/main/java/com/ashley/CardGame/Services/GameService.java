package com.ashley.CardGame.Services;

import com.ashley.CardGame.Models.Card;
import com.ashley.CardGame.Models.Game;
import com.ashley.CardGame.Models.Player;
import com.ashley.CardGame.Responses.InGamePlayerStatusResponse;
import com.ashley.CardGame.Responses.PlayerStatusResponse;
import com.ashley.CardGame.Utilities.HandSorter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public abstract class GameService<TPlayer extends Player, TGame extends Game<TPlayer>> {
    protected TGame game;
    protected DeckService deckService;

    public GameService(TGame game) {
        this.game = game;
    }

    public abstract TPlayer addPlayer(String playerName, boolean isHost);

    protected abstract Stack<Card> createDeck();

    public abstract void startGame();

    public String getGameID() {
        return game.ID;
    }

    public PlayerStatusResponse<TPlayer> getPlayerStatus() {
        String gameID = game.ID;
        int maxNumPlayers = game.getMaxNumPlayers();
        int minNumPlayers = game.getMinNumPlayers();
        ArrayList<TPlayer> players = game.playersInTurnOrder;
        var psr = new PlayerStatusResponse<>(gameID, maxNumPlayers, minNumPlayers, players, game.isGameStarted);
        return psr;
    }

    public InGamePlayerStatusResponse<TPlayer> getInGamePlayerStatus() {
        ArrayList<TPlayer> players = game.playersInTurnOrder;
        var psr = new InGamePlayerStatusResponse<>(players);
        return psr;
    }

    // Sorts players hand by suit, then value
    private void sortPlayerHand(Player player) {
        player.hand.sort(HandSorter.instance);
    }

    // Adds card input to player's hand
    private void givePlayerCard(Player player, Card card) {
        player.hand.add(card);
    }

    private Card playCard(Player player, int cardIndex) {
        return player.hand.remove(cardIndex);
    }

    // Order of deal doesn't matter, sorts each player's hands after dealing cards - clubs, diamonds, spades, hearts
    public void deal(int numCards) {
        for (int i = 0; i < numCards; i++) {
            for (Player player : game.players.values()) {
                givePlayerCard(player, deckService.drawCard());
            }
        }

        for (Player player : game.players.values()) {
            sortPlayerHand(player);
        }

    }
}
