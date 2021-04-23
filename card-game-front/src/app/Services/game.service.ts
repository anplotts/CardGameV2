import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Card } from '../Models/Card';
import { GameStatusResponse } from '../Responses/game-status-response';
import { InGamePlayerStatusResponse } from '../Responses/in-game-player-status-response';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private rootURL = "http://52.167.171.193";
  private port = "8080";
  private URL = this.rootURL + ":" + this.port;

  constructor(private http: HttpClient) { }

  getPlayerStatus(gameID: string, playerID: string): Observable<InGamePlayerStatusResponse> {
    return this.http.get<InGamePlayerStatusResponse>(this.URL +
      `/oh/playerStatus?gameID=${encodeURIComponent(gameID)}&playerID=${encodeURIComponent(playerID)}`)
      .pipe(catchError(this.handleError<InGamePlayerStatusResponse>("getPlayerStatus"))
      );
  }

  getGameStatus(gameID: string, playerID: string): Observable<GameStatusResponse> {
    return this.http.get<GameStatusResponse>(this.URL +
      `/oh/gameStatus?gameID=${encodeURIComponent(gameID)}&playerID=${encodeURIComponent(playerID)}`)
      .pipe(catchError(this.handleError<GameStatusResponse>("getGameStatus"))
      );
  }

  submitBid(gameID: string, playerID: string, bid: number): Observable<GameStatusResponse> {
    return this.http.post<GameStatusResponse>(this.URL +
      `/oh/submitBid?gameID=${encodeURIComponent(gameID)}&playerID=${encodeURIComponent(playerID)}&bid=${encodeURIComponent(bid)}`, null)
      .pipe(catchError(this.handleError<GameStatusResponse>("submitBid"))
      );
  }

  playCard(gameID: string, playerID: string, playedCard: Card): Observable<GameStatusResponse> {
    return this.http.post<GameStatusResponse>(this.URL + `/oh/playCard?gameID=${encodeURIComponent(gameID)}&playerID=${encodeURIComponent(playerID)}&suit=${encodeURIComponent(playedCard.suit)}&value=${encodeURIComponent(playedCard.value)}`, null)
    .pipe(catchError(this.handleError<GameStatusResponse>("playCard"))
    );
  }

  /**
  * Handle Http operation that failed.
  * Let the app continue.
  * @param operation - name of the operation that failed
  * @param result - optional value to return as the observable result
  */
 private handleError<T>(operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {

    // TODO: send the error to remote logging infrastructure
    console.error(error); // log to console instead

    // Let the app keep running by returning an empty result.
    return of(result as T);
  };
}


}
