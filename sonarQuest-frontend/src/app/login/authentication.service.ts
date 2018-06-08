import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';

import {environment} from './../../environments/environment';
import {LocalStorageService} from './local-storage.service';
import {Subscriber} from 'rxjs/Subscriber';
import {Token} from './token';
import 'rxjs/add/operator/shareReplay';

@Injectable()
export class AuthenticationService {

  listener: Subscriber<boolean>[] = [];

  constructor(private http: HttpClient,
              private storageService: LocalStorageService) {
  }

  public login(username, password): Observable<void> {

    return new Observable<void>(observer => {
      const body = JSON.stringify({username, password});
      const url = `${environment.endpoint}/login`;

      this.http.post(url, body, {
        headers: new HttpHeaders().set('Content-Type', 'application/json'),
        responseType: 'json',
        observe: 'response'
      }).shareReplay().subscribe((response) => {
        if (response.body && response.status === 200) {
          const token = response.body as Token;
          this.storageService.saveJWTToken(token);
          this.onLogin();
          observer.next();
          observer.complete();
        }
      }, err => {
        observer.error(err);
      });
    });
  }

  public logout(): void {
    this.storageService.removeJWTToken();
    this.onLogout();
  }

  public isLoggedIn(): boolean {
    const token = this.storageService.getJWTToken();

    if (!token) {
      return false;
    }

    return token.hasExpired();
  }

  public onLoginLogout(): Observable<boolean> {
    return new Observable<boolean>(
      oberserver => {
        this.listener.push(oberserver);
      }
    );
  }

  private onLogin(): void {
    this.listener.forEach(l => l.next(true));
  }

  private onLogout(): void {
    this.listener.forEach(l => l.next(false));
  }

}
