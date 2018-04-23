import  { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {Truck} from "../../model/truck";
import {Order} from "../../model/order";
import {catchError, map, tap} from "rxjs/operators";
import {Observable} from "rxjs/Observable";
import 'rxjs/add/observable/throw';

@Injectable()
export class OrderService {

  baseUrl : string = 'http://localhost:8088/orders';

  filterUrl: string = 'http://localhost:8088/orders?start=?&end=?';

  private order: Order;
  private headers = new HttpHeaders({'Content-Type': 'application/json'});


  constructor(private http: HttpClient) { }

  // getOrders(from: string, to: string): Observable<Order[]> {
  //   const params = new HttpParams();
  //
  //   if (from) {
  //     params.append('start', from);
  //   }
  //
  //   if (to) {
  //     params.set('end', to);
  //   }
  //   return this.http.get(this.baseUrl +'?', { params: params })
  //     .pipe(map(this.extractData),
  //          tap(data => console.log(JSON.stringify(data))),
  //         catchError(this.handleError)
  //        );
  // }

  getOrders(From: Date, To: Date): Observable<Order[]> {
    return this.http.get(this.baseUrl + '?start=' + From + '&end=' + To)
      .pipe(map(this.extractData),
        tap(data => console.log(JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  getOrdersWithoutDate(): Observable<Order[]> {
    return this.http.get(this.baseUrl)
      .pipe(map(this.extractData),
        tap(data => console.log(JSON.stringify(data))),
        catchError(this.handleError)
      );
  }

  updateOrder(order: Order): Observable<number> {
    return this.http.put(this.baseUrl, JSON.stringify(this.order), {headers: this.headers})
      .pipe(map(this.extractData),
        catchError(this.handleError));
  }

  deleteOrderById(orderId: number) {
    return this.http.delete(this.baseUrl + "/" + orderId)
      .pipe(
        map(this.extractData),
        catchError(this.handleError)
      );
  }

  setter(order: Order) {
    this.order = order;
  }

  getter() {
    return this.order;
  }

  createOrder(order: Order): Observable<number> {
    return this.http.post(this.baseUrl, JSON.stringify(order), {headers: this.headers})
      .pipe(map(this.extractData),
        catchError(this.handleError));
  }

  getOrderById(id: Number): Observable<number> {
    return this.http.get(this.baseUrl + '/' + id, {headers: this.headers})
      .pipe(map(extractData => extractData),
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
      errorMessage = `server side  ${error.status}, body was: ${error.error}`;
    }

    console.error(error.ok || error);
    return Observable.throw(error.status);
  }
}
