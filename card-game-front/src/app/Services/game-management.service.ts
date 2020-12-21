import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { GameStatus } from '../Models/game-status';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { JoinGameResponse } from '../Models/join-game-response';

@Injectable({
  providedIn: 'root'
})
export class GameManagementService {

  private rootURL = "http://localhost";
  private port = "8080";
  private URL = this.rootURL + ":" + this.port;

  constructor(private http: HttpClient) { }

  createNewGame(hostName: string, selectedGame: string): Observable<JoinGameResponse> {
    return this.http.post<JoinGameResponse>(this.URL +
      `/createNewGame?hostName=${encodeURIComponent(hostName)}&selectedGame=${encodeURIComponent(selectedGame)}`, null)
      .pipe(catchError(this.handleError<JoinGameResponse>("createNewGame"))
      );
  }

  joinGame(playerName: string, gameID: string): Observable<JoinGameResponse> {
    return this.http.put<JoinGameResponse>(this.URL +
      `/joinGame?playerName=${encodeURIComponent(playerName)}&gameID=${encodeURIComponent(gameID)}`, null)
      .pipe(catchError(this.handleError<JoinGameResponse>("joinGame"))
      );
  }

  getGameStatus(gameID: string): Observable<GameStatus> {
    return this.http.get<GameStatus>(this.URL + 
      `/gameStatus?gameID=${encodeURIComponent(gameID)}`)
      .pipe(catchError(this.handleError<GameStatus>("getGameStatus"))
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