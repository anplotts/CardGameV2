import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NewGameResponse } from '../Models/new-game-response';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GameManagementService {

  private rootURL = "http://localhost";
  private port = "8080";
  private URL = this.rootURL + ":" + this.port ;

  constructor(private http: HttpClient) { }

  createNewGame(hostName: string, selectedGame: string): Observable<NewGameResponse> {
    return this.http.get<NewGameResponse>(this.URL + "/createNewGame").pipe(
      catchError(this.handleError<NewGameResponse>("createNewGame"))
    );
  }

  joinGame(): void {

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