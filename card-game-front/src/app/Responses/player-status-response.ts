import { Player } from "../Models/Player";

export interface PlayerStatusResponse {
    ID: string;
    players: Array<Player>;
    maxNumPlayers: number;
    minNumPlayers: number;
    isGameStarted: boolean;
}