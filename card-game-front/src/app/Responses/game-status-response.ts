import { Card } from "../Models/Card";
import { Player } from "../Models/Player";

export interface GameStatusResponse {
    players: Array<Player>;
    currentTrump: Card;
    playerHand: Array<Card>;
    timeToBid: boolean;
    timeToPlay: boolean;
    playableCards: Array<Card>;
    winner: Player;
    invalidBid: Number;
    maxBid: Number;
}