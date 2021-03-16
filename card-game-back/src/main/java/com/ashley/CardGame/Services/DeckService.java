package com.ashley.CardGame.Services;

import com.ashley.CardGame.Models.Card;
import com.ashley.CardGame.Models.Deck;

import java.util.Collections;
import java.util.Stack;

public class DeckService {
    private Deck deck;

    public DeckService(Stack<Card> deck) {
        this.deck = new Deck(deck);
    }

    // Creates a draw pile by cloning the deck stack
    private void initializeDrawPile() {
        deck.drawPile = (Stack<Card>)deck.deck.clone();
    }

    // Calls initializeDrawPile to create a draw pile, then shuffles the draw pile
    public void shuffle() {
        initializeDrawPile();
        Collections.shuffle(deck.drawPile);
    }

    // Pops the top card from the draw pile stack and returns it
    public Card drawCard() {
        return deck.drawPile.pop();
    }

    // Adds the card input to the top of the discard pile stack
    public void discard(Card card) {
        deck.discardPile.push(card);
    }

    // Shuffles the discard pile and sets it as the new draw pile, for use when draw pile is empty
    public void shuffleDiscard() {
        Collections.shuffle(deck.discardPile);
        deck.drawPile = deck.discardPile;
        deck.discardPile = new Stack<>();
    }

    // Returns the size of the Draw Pile
    public int getDrawPileSize() {
        return deck.drawPile.size();
    }


    public Card drawFromDiscard() {
        return deck.discardPile.pop();
    }

}
