import { Component, Input } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { GameManagementService } from './Services/game-management.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'card-game-front';
  gameID: string;
  joinGameID: string;
  closeResult = '';
  hostName: string = '';
  playerName: string = '';
  selectedGame: string = '';
  gameOptions: string[];

  constructor(private gms: GameManagementService, private modalService: NgbModal) {
    this.gameOptions = ["Oh Hell", "No other games available"];
  }

  createNewGame(): void {
    this.gms.createNewGame(this.hostName, this.selectedGame)
      .subscribe(response => this.gameID = response.ID);
  }

  joinGame(): void {
    this.gms.joinGame(this.playerName, this.joinGameID)
      .subscribe(response => this.gameID = response.ID);;
  }

  open(content): void {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' });
  }


}

