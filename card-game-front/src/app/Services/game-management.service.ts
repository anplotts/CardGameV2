import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PlayerStatusResponse } from '../Responses/player-status-response';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { JoinGameResponse } from '../Responses/join-game-response';

@Injectable({
  providedIn: 'root'
})
export class GameManagementService {

  private rootURL = "http://192.168.50.250";
  private port = "8080";
  private URL = this.rootURL + ":" + this.port;

  constructor(private http: HttpClient) { }

  createNewGame(hostName: string, selectedGame: string): Observable<JoinGameResponse> {
    return this.http.post<JoinGameResponse>(this.URL +
      `/gm/createNewGame?hostName=${encodeURIComponent(hostName)}&selectedGame=${encodeURIComponent(selectedGame)}`, null)
      .pipe(catchError(this.handleError<JoinGameResponse>("createNewGame"))
      );
  }

  joinGame(playerName: string, gameID: string): Observable<JoinGameResponse> {
    return this.http.put<JoinGameResponse>(this.URL +
      `/gm/joinGame?playerName=${encodeURIComponent(playerName)}&gameID=${encodeURIComponent(gameID)}`, null)
      .pipe(catchError(this.handleError<JoinGameResponse>("joinGame"))
      );
  }

  getPlayerStatus(gameID: string, playerID: string): Observable<PlayerStatusResponse> {
    return this.http.get<PlayerStatusResponse>(this.URL +
      `/gm/playerStatus?gameID=${encodeURIComponent(gameID)}&playerID=${encodeURIComponent(playerID)}`)
      .pipe(catchError(this.handleError<PlayerStatusResponse>("getPlayerStatus"))
      );
  }

  startGame(gameID: string): Observable<PlayerStatusResponse> {
    return this.http.post<PlayerStatusResponse>(this.URL +
      `/gm/startGame?gameID=${encodeURIComponent(gameID)}`, null)
      .pipe(catchError(this.handleError<PlayerStatusResponse>("startGame"))
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