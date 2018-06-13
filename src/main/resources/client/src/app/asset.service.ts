import { Injectable } from '@angular/core';
import { Asset } from './asset/asset';
import { Observable, of } from 'rxjs';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {catchError, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AssetService {

  // URL of the assets address on the server
  private assetsUrl: string = 'api/assets';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  getAssets() : Observable<Asset[]>{

    this.log('fetched assets');
    // fetch from server
    return this.http.get<Asset[]>(this.assetsUrl)
    .pipe(
      tap(assets => this.log(`fetched ${assets.length} assets`)),
      catchError(this.handleError('getAssets', []))
    );
  }

  getAsset(id: number){
    const url = `${this.assetsUrl}/${id}`;

    return this.http.get<Asset>(url)
    .pipe(
      tap(_ => this.log(`fetched hero id=${id}`)),
      catchError(this.handleError<Asset>(`getAsset id=${id}`))
    );
  }

  /**PUT: update the asset on the server */
  updateAsset(asset: Asset): Observable<Asset> {
    
    return this.http.put(this.assetsUrl, asset,this.httpOptions)
    .pipe(
        tap(() => this.log(`updated asset id=${asset.id}`)),
        catchError(this.handleError<any>('updateAsset'))
      );
  }

  /**POST: add a new asset on the server */
  addAsset(asset: Asset): Observable<Asset> {
    
    return this.http.post<Asset>(this.assetsUrl, asset, this.httpOptions)
    .pipe(
      tap((asset: Asset) => this.log(`added asset w/id=${asset.id}`)),
      catchError(this.handleError<Asset>('addHero'))
    );
  }

  /**DELETE: delete the asset from the server */
  deleteAsset(asset: Asset | number): Observable<Asset> {
    const id = typeof asset === 'number' ? asset : asset.id;
    const url = `${this.assetsUrl}/${id}`;

    return this.http.delete<Asset>(url, this.httpOptions)
    .pipe(
      tap(() => this.log(`deleted asset id=${id}`)),
      catchError(this.handleError<Asset>('deleteAsset'))
    );
  }

  /**GET: assets whose name contains search term */
  searchAssets(term: string): Observable<Asset[]>{
      if( !term.trim()){
        // return empty array
        return of([]);
      }
      return this.http.get<Asset[]>(`${this.assetsUrl}/?name=${term}`)
      .pipe(
        tap(() => this.log(`found assets matching "${term}`)),
        catchError(this.handleError<Asset[]>('searchAssets', []))
      );

  }

  private handleError<T>(operation = 'operation', result?: T){

    return (error: any): Observable<T> => {
      //send error to remote logging infrastructure
      console.error(error);// log to console

      // transform for view on screen
      this.log(`${operation} failed: ${error.message}`);

      // return empty result
      return of(result as T);
    }
  }

  private log(message: string){
    this.messageService.add('AssetService: '+ message);
  }
}
