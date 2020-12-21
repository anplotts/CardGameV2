import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { GameManagementService } from '../Services/game-management.service';
import { WaitingRoomComponent } from '../waiting-room/waiting-room.component';

@Component({
  selector: 'app-start-game',
  templateUrl: './start-game.component.html',
  styleUrls: ['./start-game.component.css']
})
export class StartGameComponent {

  title = 'card-game-front';
  gameID: string;
  joinGameID: string;
  closeResult = '';
  hostName: string = '';
  playerName: string = '';
  selectedGame: string = '';
  gameOptions: string[];

  constructor(private gms: GameManagementService, private modalService: NgbModal, private router: Router) {
    this.gameOptions = ["Oh Hell", "No other games available"];
  }

  createNewGame(): void {
    this.gms.createNewGame(this.hostName, this.selectedGame)
      .subscribe(response => this.router.navigate([`../waitingroom/${response.gameID}`], {queryParams: {playerName: response.playerName, playerID: response.playerID}}));
  }

  joinGame(): void {
    this.gms.joinGame(this.playerName, this.joinGameID)
      .subscribe(response => this.router.navigate([`../waitingroom/${response.gameID}`], {queryParams: {playerName: response.playerName, playerID: response.playerID}}));;
  }

  open(content): void {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' });
  }


}
