import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";

import {Truck} from "../../model/truck";

//use both throw and observable or import all observable like below commented
import {Observable} from "rxjs/Observable";
import 'rxjs/add/observable/throw';
//import {Observable} from 'rxjs';
import {catchError, map, tap} from "rxjs/operators";
import {TruckDetail} from "../../model/truckDetail";

@Injectable()
export class TruckService {

  private baseUrl: string = 'http://localhost:8088/trucks';

  private truck: Truck;
  private headers = new HttpHeaders({'Content-Type': 'application/json'});


  constructor(private http: HttpClient) {
  }

  getTrucks(): Observable<Truck[]> {
    return this.http.get(this.baseUrl)
      .pipe(
        map(this.extractData),
        tap(data => console.log(JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  deleteTruckById(truckId: number) {
    return this.http.delete(this.baseUrl + "/" + truckId)
      .pipe(
        map(this.extractData),
        catchError(this.handleError)
      );
  }

  updateTrucks(truck: Truck) {
    return this.http.put(this.baseUrl, JSON.stringify(truck), {headers: this.headers})
      .pipe(map(this.extractData),
        tap(data => console.log(JSON.stringify(data))),
        catchError(this.handleError));
  }

  setter(truck: Truck) {
    this.truck = truck;
  }

  getter() {
    return this.truck;
  }

  createTruck(truck: Truck): Observable<number> {
    return this.http.post(this.baseUrl, JSON.stringify(truck), {headers: this.headers})
      .pipe(map(this.extractData),
        catchError(this.handleError));
  }

  getTruckById(id: number): Observable<TruckDetail> {
    return this.http.get(this.baseUrl + "/" + id, {headers: this.headers})
      .pipe(map(this.extractData),
        catchError(this.handleError));
  }

  private extractData(response: HttpResponse<Truck>) {
    const body = response;
    console.log(body)
    return body;
  }


  private handleError(error: HttpErrorResponse| any) {
    let errorMessage: string;

    // A client-side or network error occurred.
    if (error.error instanceof Error) {
      errorMessage = `An error occurred: ${error.error.message}`;

    } else {
      errorMessage = `server side  ${error.message}, body was: ${error.error}`;
    }

    console.error(error.message || error);
    return Observable.throw(error.status);
  }

}
