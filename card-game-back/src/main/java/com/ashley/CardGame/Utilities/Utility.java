package com.ashley.CardGame.Utilities;

public class Utility {

    public static String generateID(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String ID = "";

        for (int i = 0; i < length; i++) {
            int index = (int) (alphabet.length() * Math.random());
            ID += alphabet.charAt(index);
        }

        return ID;
    }

}
