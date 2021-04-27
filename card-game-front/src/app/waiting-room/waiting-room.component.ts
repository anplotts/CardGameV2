import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GameManagementService } from '../Services/game-management.service';
import { interval } from 'rxjs';
import { Router } from '@angular/router';
import { Player } from '../Models/Player';

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
  playersList: Array<Player>;
  playerIsHost: boolean;
  waitingPlayerSub: any;

  constructor(
    private route: ActivatedRoute,
    private gms: GameManagementService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.gameID = this.route.snapshot.paramMap.get("id");
    this.playerName = this.route.snapshot.queryParamMap.get("playerName");
    this.playerID = this.route.snapshot.queryParamMap.get("playerID");
    this.updatePlayerStatus();
    this.waitingPlayerSub = interval(1000).subscribe(x => this.updatePlayerStatus());
  }

  updatePlayerStatus(): void {
    this.gms.getPlayerStatus(this.gameID, this.playerID)
      .subscribe(response => {
        this.maxNumPlayers = response.maxNumPlayers;
        this.minNumPlayers = response.minNumPlayers;
        this.playerCount = response.players.length;
        this.playersList = response.players;

        let player = this.playersList.find(player => this.playerID == player.ID);
        this.playerIsHost = player.isHost;

        if (response.isGameStarted) {
          this.waitingPlayerSub.unsubscribe();
          this.router.navigate([`../gameplay/${this.gameID}`], { queryParams: { playerName: this.playerName, playerID: this.playerID } })
        }
      });

  }

  startGame(): void {
    this.waitingPlayerSub.unsubscribe();
    this.gms.startGame(this.gameID)
      .subscribe(response => this.router.navigate([`../gameplay/${this.gameID}`], { queryParams: { playerName: this.playerName, playerID: this.playerID } }));;
  }

}