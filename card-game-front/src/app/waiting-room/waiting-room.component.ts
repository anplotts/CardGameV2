import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GameManagementService } from '../Services/game-management.service';
import { interval } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-waiting-room',
  templateUrl: './waiting-room.component.html',
  styleUrls: ['./waiting-room.component.css']
})
export class WaitingRoomComponent implements OnInit {
  gameID: string;
  playerCount: number;
  maxNumPlayers: number;
  minNumPlayers: number;
  playerName: string;
  playerID: string;
  playersList: Array<any>;
  playerIsHost: boolean;

  constructor(
    private route: ActivatedRoute,
    private gms: GameManagementService
  ) { }

  ngOnInit(): void {
    this.gameID = this.route.snapshot.paramMap.get("id");
    this.playerName = this.route.snapshot.queryParamMap.get("playerName");
    this.playerID = this.route.snapshot.queryParamMap.get("playerID");
    interval(1000).subscribe(x => this.updateGameStatus());
  }

  updateGameStatus(): void {
    this.gms.getGameStatus(this.gameID)
      .subscribe(response => { 
        this.maxNumPlayers = response.maxNumPlayers;
        this.minNumPlayers = response.minNumPlayers;
        this.playerCount = response.playerCount;
        this.playersList = response.playersList;

        let player = this.playersList.find(player => this.playerID == player.id);
        this.playerIsHost = player.isHost;
      });

  }

  startGame(): void {

  }

}