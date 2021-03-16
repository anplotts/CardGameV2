package com.ashley.CardGame.Models;

public class Card {
    public int value;
    public String suit;
    public static final String HEARTS = "Hearts";
    public static final String DIAMONDS = "Diamonds";
    public static final String SPADES = "Spades";
    public static final String CLUBS = "Clubs";


    // Creates a Card given a suit and value input
    public Card(int value, String suit) {
        this.value = value;
        this.suit = suit;
    }
}
