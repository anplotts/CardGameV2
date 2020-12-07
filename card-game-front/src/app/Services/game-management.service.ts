import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NewGame } from '../Models/new-game';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GameManagementService {

  private rootURL = "http://localhost";
  private port = "8080";
  private URL = this.rootURL + ":" + this.port;

  constructor(private http: HttpClient) { }

  createNewGame(hostName: string, selectedGame: string): Observable<NewGame> {
    return this.http.post<NewGame>(this.URL +
      `/createNewGame?hostName=${encodeURIComponent(hostName)}&selectedGame=${encodeURIComponent(selectedGame)}`, null)
      .pipe(catchError(this.handleError<NewGame>("createNewGame"))
      );
  }

  joinGame(playerName: string, gameID: string): Observable<any> {
    return this.http.put<any>(this.URL +
      `/joinGame?playerName=${encodeURIComponent(playerName)}&gameID=${encodeURIComponent(gameID)}`, null)
      .pipe(catchError(this.handleError<any>("joinGame"))
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