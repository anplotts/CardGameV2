package com.ashley.CardGame.Models;

import java.util.Stack;

public class Deck {
    public final Stack<Card> deck;
    public Stack<Card> drawPile;
    public Stack<Card> discardPile = new Stack<>();

    // Takes a stack of Card objects as input to create a deck of cards
    public Deck(Stack<Card> deck) {
        this.deck = deck;
    }
}
