import {Response} from '@angular/http';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {Subject} from 'rxjs/Subject';
import {Artefact} from './../Interfaces/Artefact';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class ArtefactService {

  private artefactsSubject: Subject<Artefact[]> = new ReplaySubject(1);
  artefacts$ = this.artefactsSubject.asObservable();

  private artefactsforMarkteplaceSubject: Subject<Artefact[]> = new ReplaySubject(1);
  artefactsforMarkteplace$ = this.artefactsforMarkteplaceSubject.asObservable();

  constructor(public http: HttpClient) {
  }

  getData(): void {
    this.getArtefacts();
    this.getArtefactsforMarkteplace();
  }

  getArtefacts(): Observable<Artefact[]> {
    this.http.get<Artefact[]>(`${environment.endpoint}/artefact/`)
      .subscribe(
        result => this.artefactsSubject.next(result),
        err => this.artefactsSubject.error(err)
      );
    return this.artefactsSubject.asObservable();
  }

  getArtefactsforMarkteplace(): Observable<Artefact[]> {
    this.http.get<Artefact[]>(`${environment.endpoint}/artefact/forMarketplace/`)
      .subscribe(
        result => this.artefactsforMarkteplaceSubject.next(result),
        err => this.artefactsforMarkteplaceSubject.error(err)
      );
    return this.artefactsSubject.asObservable();
  }

  createArtefact(artefact: any): Promise<Artefact> {
    return this.http.post<Artefact>(`${environment.endpoint}/artefact/`, artefact)
      .toPromise()
      .catch(this.handleError);
  }

  updateArtefact(artefact: any): Promise<Artefact> {
    return this.http.put<Artefact>(`${environment.endpoint}/artefact/${artefact.id}`, artefact)
      .toPromise()
      .catch(this.handleError);
  }

  buyArtefact(artefact: Artefact): Promise<boolean> {
    return this.http.put<boolean>(`${environment.endpoint}/artefact/${artefact.id}/buy`, null)
      .toPromise()
      .catch(this.handleError);
  }

  private handleError(error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Promise.reject(errMsg);
  }

  setMinLevel(artefact: Artefact, min: number) {
    artefact.minLevel.min = min
  }
}
