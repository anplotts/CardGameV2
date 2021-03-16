package com.ashley.CardGame.Services;

import com.ashley.CardGame.Models.Card;
import com.ashley.CardGame.Models.OhHell;
import com.ashley.CardGame.Models.OhHellPlayer;
import com.ashley.CardGame.Models.Player;
import com.ashley.CardGame.Responses.OhHellStatusResponse;
import com.ashley.CardGame.Utilities.OhHellGameWinner;
import com.ashley.CardGame.Utilities.OhHellHandWinner;

import java.util.*;

public class OhHellService extends GameService<OhHellPlayer, OhHell> {

    public OhHellService() {
        super(new OhHell());
    }

    @Override
    public OhHellPlayer addPlayer(String playerName, boolean isHost) {
        OhHellPlayer player = new OhHellPlayer(playerName, isHost);
        this.game.players.put(player.ID, player);
        this.game.playersInTurnOrder.add(player);
        return player;
    }

    @Override
    protected Stack<Card> createDeck() {
        List<String> suits = Arrays.asList(Card.SPADES, Card.CLUBS, Card.HEARTS, Card.DIAMONDS);
        List<Integer> values = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        Stack<Card> cards = new Stack<>();
        Card card;

        for (String suit : suits) {
            for (Integer value : values) {
                card = new Card(value, suit);
                cards.push(card);
            }
        }

        return cards;
    }

    @Override
    public void startGame() {
        game.isGameStarted = true;
        Collections.shuffle(game.playersInTurnOrder);
        deckService = new DeckService(createDeck());
        startNewRound();
    }

    public OhHellStatusResponse<OhHellPlayer> getGameStatus(String playerID) {
        boolean timeToBid = isTimeForPlayerBid(playerID);
        boolean timeToPlay = isTimeForPlayerToPlay(playerID);
        int invalidBid = invalidBidCalculator(playerID);
        int maxBid = game.roundNum < 11 ? 11 - game.roundNum : game.roundNum - 9;
        var playableCards = getPlayableCards(playerID);
        var gsr = new OhHellStatusResponse<>(game.playersInTurnOrder,
                game.currentTrump,
                game.players.get(playerID).hand,
                timeToBid,
                timeToPlay,
                playableCards,
                game.winner,
                invalidBid,
                maxBid);
        return gsr;
    }

    private boolean isTimeForPlayerBid(String playerID) {
        String currentBidder = "";

        for (int i = 0; i < game.playersInTurnOrder.size(); i++) {
            int currentIndex = (i + game.firstBidder) % game.playersInTurnOrder.size();

            if (game.playersInTurnOrder.get(currentIndex).currentBid == OhHellPlayer.NO_BID) {
                currentBidder = game.playersInTurnOrder.get(currentIndex).ID;
                break;
            }
        }
        return currentBidder.equals(playerID);
    }

    private boolean isTimeForPlayerToPlay(String playerID) {
        String currentPlayer = "";

        if (game.playersInTurnOrder.stream().anyMatch(player -> player.currentBid == OhHellPlayer.NO_BID)) {
            return false;
        }

        for (int i = 0; i < game.playersInTurnOrder.size(); i++) {
            int currentIndex = (i + game.firstPlayer) % game.playersInTurnOrder.size();

            if (game.playersInTurnOrder.get(currentIndex).currentPlayedCard == null) {
                currentPlayer = game.playersInTurnOrder.get(currentIndex).ID;
                break;
            }
        }

        return currentPlayer.equals(playerID);
    }

    private int invalidBidCalculator(String playerID) {
        int lastIndex = ((game.playersInTurnOrder.size() - 1) + game.firstBidder) % game.playersInTurnOrder.size();
        boolean isLastBidder = false;
        int totalBids = 0;
        int invalidBid = -1;
        int numCardsForRound = game.roundNum < 11 ? 11 - game.roundNum : game.roundNum - 9;

        if (game.playersInTurnOrder.get(lastIndex).ID.equals(playerID)) {
            isLastBidder = true;
        }

        if (isLastBidder) {
            for (int i = 0; i < game.playersInTurnOrder.size() - 1; i++) {
                int currentIndex = (i + game.firstPlayer) % game.playersInTurnOrder.size();
                totalBids += game.playersInTurnOrder.get(currentIndex).currentBid;
            }
            invalidBid = numCardsForRound - totalBids;
        }

        return invalidBid;
    }

    public void submitBid(String playerID, int bid) {
        game.players.get(playerID).currentBid = bid;
    }

    public void playCard(String playerID, Card playedCard) {
        game.players.get(playerID).currentPlayedCard = playedCard;
        game.players.get(playerID).hand.removeIf(card -> card.suit.equals(playedCard.suit) && card.value == playedCard.value);

        if (game.playersInTurnOrder.get(game.firstPlayer).ID.equals(playerID)) {
            game.leadSuit = playedCard.suit;
        }

        if (playedCard.suit.equals(game.currentTrump.suit)) {
            game.isTrumpBroken = true;
        }
    }

    public void updateGameState() {
        if (game.playersInTurnOrder.stream().anyMatch(player -> player.currentPlayedCard == null)) {
            return;
        }

        OhHellPlayer winnerOfHand = determineWinnerOfHand(game.currentTrump.suit, game.leadSuit);
        winnerOfHand.currentTricksWon++;
        game.firstPlayer = game.playersInTurnOrder.indexOf(winnerOfHand);

        if (game.playersInTurnOrder.stream().allMatch(player -> player.hand.size() == 0)) {
            scoreRound();
            if (game.roundNum < 2) {
                resetRound();
                startNewRound();
            }
            else {
                game.winner = endGame();
            }
        }

        game.leadSuit = "";

        for (OhHellPlayer player : game.playersInTurnOrder) {
            player.currentPlayedCard = null;
        }
    }

    private void incrementFirstBidder() {
        if (game.firstBidder == game.playersInTurnOrder.size() - 1) {
            game.firstBidder = 0;
        }
        game.firstBidder++;
    }

    private OhHellPlayer determineWinnerOfHand(String trumpSuit, String leadCardSuit) {
        var playersCopy = new ArrayList<>(game.playersInTurnOrder);
        playersCopy.sort(new OhHellHandWinner(trumpSuit, leadCardSuit));

        return playersCopy.get(playersCopy.size() - 1);
    }

    private void scoreRound() {
        for (OhHellPlayer player : game.playersInTurnOrder) {
            if (player.currentTricksWon == player.currentBid) {
                player.score += (10 + player.currentBid);
            }
        }
    }

    private void resetRound() {
        incrementFirstBidder();
        game.firstPlayer = game.firstBidder;
        game.roundNum++;
        game.isTrumpBroken = false;

        for (OhHellPlayer player : game.playersInTurnOrder) {
            player.currentTricksWon = 0;
            player.currentBid = OhHellPlayer.NO_BID;
            player.currentPlayedCard = null;
        }
    }

    private void startNewRound() {
        int numCardsForRound = game.roundNum < 11 ? 11 - game.roundNum : game.roundNum - 9;

        deckService.shuffle();
        deal(numCardsForRound);
        game.currentTrump = deckService.drawCard();
    }

    private OhHellPlayer endGame() {
        var playersCopy = new ArrayList<>(game.playersInTurnOrder);
        Collections.sort(playersCopy, new OhHellGameWinner());
        return playersCopy.get(game.playersInTurnOrder.size() - 1);
    }

    private ArrayList<Card> getPlayableCards(String playerID) {
        ArrayList<Card> playableCards = new ArrayList<>();
        ArrayList<Card> hand = game.players.get(playerID).hand;
        boolean containsLeadSuit = hand.stream().anyMatch(card -> card.suit.equals(game.leadSuit));
        boolean handAllTrump = hand.stream().allMatch(card -> card.suit.equals(game.currentTrump.suit));
        boolean isFirstPlayer = game.playersInTurnOrder.get(game.firstPlayer).ID.equals(playerID);

        for (Card card : hand) {
            if (isFirstPlayer && !game.isTrumpBroken && card.suit.equals(game.currentTrump.suit) && !handAllTrump) {
                continue;
            }

            if (!isFirstPlayer && containsLeadSuit && !card.suit.equals(game.leadSuit)) {
                continue;
            }

            playableCards.add(card);
        }

        return playableCards;
    }
}


