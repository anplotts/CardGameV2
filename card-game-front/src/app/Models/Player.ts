import { Card } from "./Card";

export interface Player {
    name: string;
    ID: string;
    isHost: boolean;
    isConnected: boolean;
    currentBid: number;
    currentPlayedCard: Card;
    currentTricksWon: number;
    score: number;
    tricksWonClass: string;
}