import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GameService } from '../Services/game.service';
import { interval, Observable } from 'rxjs';
import { Player } from '../Models/Player';
import { Card } from '../Models/Card';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GameStatusResponse } from '../Responses/game-status-response';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';

@Component({
  selector: 'app-gameplay',
  templateUrl: './gameplay.component.html',
  styleUrls: ['./gameplay.component.css']
})
export class GameplayComponent implements OnInit {
  gameID: string;
  playerName: string;
  playerID: string;
  playerStatuses: Array<any>;
  allPlayers: Array<Player>;
  currentTrump: Card;
  playerHand: Array<Card>;
  playableCards: Array<Card>;
  timeToBid: boolean;
  timeToPlay: boolean;
  selectedCard: Card;
  myPlayer: Player;
  otherPlayers: Array<Player>;
  waitingForGameStatus: boolean;
  waitingForPlayerStatus: boolean;
  winner: Player;
  playerStatusSub: any;
  gameStatusSub: any;
  handEndSub: any;
  tricksWonSub: any;
  invalidBid: Number;
  maxBid: Number;
  endOfHand: boolean;

  bidForm = new FormGroup({
    bid: new FormControl('', [Validators.required, Validators.min(0), this.invalidBidValidator(), this.maxBidValidator()])
  });

  @ViewChild('bidModal') bidModal: TemplateRef<any>;
  @ViewChild('gameEndModal') gameEndModal: TemplateRef<any>;

  constructor(private gs: GameService, private route: ActivatedRoute, private modalService: NgbModal, private router: Router) { }

  ngOnInit(): void {
    this.gameID = this.route.snapshot.paramMap.get("id");
    this.playerName = this.route.snapshot.queryParamMap.get("playerName");
    this.playerID = this.route.snapshot.queryParamMap.get("playerID");
    this.updatePlayerStatus();
    this.updateGameStatus();
    this.playerStatusSub = interval(5000).subscribe(x => this.updatePlayerStatus());
    this.gameStatusSub = interval(1000).subscribe(x => this.updateGameStatus());
  }

  updatePlayerStatus(): void {
    if (this.waitingForPlayerStatus) {
      return;
    }
    this.waitingForPlayerStatus = true;
    this.gs.getPlayerStatus(this.gameID, this.playerID)
      .subscribe(response => this.playerStatuses = response.playerStatuses, null, () => this.waitingForPlayerStatus = false);

  }

  open(content): void {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' });
  }

  updateGameStatus(): void {
    if (this.timeToBid || this.timeToPlay || this.waitingForGameStatus || this.endOfHand) {
      return;
    }
    this.waitingForGameStatus = true;
    this.gs.getGameStatus(this.gameID, this.playerID)
      .subscribe(response => this.updateGameStatusFromResponse(response), null, () => this.waitingForGameStatus = false);
  }

  submitBid(): void {
    this.gs.submitBid(this.gameID, this.playerID, this.bidForm.value.bid)
      .subscribe(response => this.updateGameStatusFromResponse(response));
  }

  updateGameStatusFromResponse(response: GameStatusResponse): void {
    if (this.allPlayers) {
      for (let player of response.players) {
        if (player.currentTricksWon !== this.allPlayers.find(oldPlayer => oldPlayer.ID === player.ID).currentTricksWon) {
          player.tricksWonClass = "trick-winner";
          this.tricksWonSub = interval(5000).subscribe(x => this.updateTricksWonClass(player));
        }
      }
    }

    this.allPlayers = response.players;
    this.currentTrump = response.currentTrump;
    this.playerHand = response.playerHand;
    this.timeToBid = response.timeToBid;
    this.timeToPlay = response.timeToPlay;
    this.playableCards = response.playableCards;
    this.invalidBid = response.invalidBid;
    this.maxBid = response.maxBid;

    let myUpdatedPlayer = this.allPlayers.find(player => player.ID === this.playerID);

    let cardChangedFromNullToNonNull = this.myPlayer && !this.myPlayer.previousPlayedCard && myUpdatedPlayer.previousPlayedCard;
    console.log(`cardChangedFromNullToNonNull ${cardChangedFromNullToNonNull}`);
    let cardChangedSuit = this.myPlayer && this.myPlayer.previousPlayedCard && myUpdatedPlayer.previousPlayedCard &&
      this.myPlayer.previousPlayedCard.suit !== myUpdatedPlayer.previousPlayedCard.suit;
    console.log(`cardChangedSuit ${cardChangedSuit}`);
    let cardChangedValue = this.myPlayer && this.myPlayer.previousPlayedCard && myUpdatedPlayer.previousPlayedCard &&
      this.myPlayer.previousPlayedCard.value !== myUpdatedPlayer.previousPlayedCard.value;
    console.log(`cardChangedValue ${cardChangedValue}`);

    if (cardChangedFromNullToNonNull || cardChangedSuit || cardChangedValue) {
      this.endOfHand = true;
      for (let player of this.allPlayers) {
        player.cardToDisplay = player.previousPlayedCard;
      }
      this.handEndSub = interval(5000).subscribe(x => this.changeEndOfHandBool());
    }
    else {
      for (let player of this.allPlayers) {
        player.cardToDisplay = player.currentPlayedCard;
      }
    }

    this.myPlayer = this.allPlayers.find(player => player.ID === this.playerID);
    this.otherPlayers = this.allPlayers.filter(player => player.ID !== this.playerID);

    if (response.winner) {
      this.open(this.gameEndModal);
      this.winner = response.winner;
      this.playerStatusSub.unsubscribe();
      this.gameStatusSub.unsubscribe();
    }

    if (this.timeToBid && !this.endOfHand) {
      this.open(this.bidModal);
    }
  }

  selectCard(card: Card): void {
    if (!this.isCardPlayable(card)) {
      return;
    }

    if (this.selectedCard) {
      this.selectedCard.style = "";
    }

    this.selectedCard = card;
    this.selectedCard.style = "transform: scale(1.2)";
  }

  playCard(): void {
    this.gs.playCard(this.gameID, this.playerID, this.selectedCard)
      .subscribe(response => this.updateGameStatusFromResponse(response));
  }

  getClassForCard(card: Card): string {
    if (this.isCardPlayable(card)) {
      return "playable";
    }
    return "nonplayable";
  }

  isCardPlayable(card: Card): boolean {
    if (this.timeToPlay && !this.endOfHand &&
      this.playableCards.find(cardFromList => card.suit === cardFromList.suit && card.value === cardFromList.value)) {
      return true;
    }
    else {
      return false;
    }
  }

  formatBid(bid: number): string {
    if (bid === -1) {
      return "None";
    }
    return `${bid}`;
  }

  goHome(): void {
    this.router.navigate(['']);
  }

  updateTricksWonClass(player: Player): void {
    player.tricksWonClass = '';
    this.tricksWonSub.unsubscribe();
  }

  invalidBidValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const invalidBid = this.invalidBid;
      return control.value == invalidBid ? { invalidBid: { value: invalidBid } } : null;
    }
  }

  maxBidValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const maxBid = this.maxBid;
      return control.value > maxBid ? { maxBid: { value: maxBid } } : null;
    }
  }

  changeEndOfHandBool(): void {
    this.endOfHand = false;
    this.handEndSub.unsubscribe();

    for (let player of this.allPlayers) {
      player.cardToDisplay = null;
    }

    if (this.timeToBid) {
      this.open(this.bidModal);
    }
  }
}
