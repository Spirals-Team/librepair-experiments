import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import { isNil } from 'lodash';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { authorizationPath } from '../paths/AuthorizationPaths';
import { catchError, map } from 'rxjs/operators';
import { ErrorHandler } from '../helpers/ErrorHandler';

const jwtHelper = new JwtHelperService();

interface IAuthResponse {
  token: string;
}

@Injectable()
export class AuthService {
  public token: string;

  constructor(private http: HttpClient) {
    this.token = AuthService.getToken();
  }

  public static getToken(): string {
    return localStorage.getItem('token');
  }

  public static getUser(): string {
    return localStorage.getItem('currentUser');
  }

  public static setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  public static setUser(user: string): void {
    localStorage.setItem('currentUser', user);
  }

  public static removeSession() {
    localStorage.removeItem('token');
    localStorage.removeItem('currentUser');
  }

  public isAuthenticated(): boolean {
    if (isNil(this.token)) {
      return false;
    }

    if (isNil(AuthService.getUser())) {
      return false;
    }

    return !jwtHelper.isTokenExpired(this.token);
  }


  public login(username: string, password: string): Observable<boolean> {
    return this.http.post(authorizationPath, { username: username, password: password })
      .pipe(
        map((response: IAuthResponse) => {
          const token = response.token;
          if (token) {
            this.token = token;
            AuthService.setToken(token);
            AuthService.setUser(username);
            return true;
          } else {
            return false;
          }
        }),
        catchError(ErrorHandler.handleError)
      );
  }

  public logout(): void {
    this.token = null;
    AuthService.removeSession();
  }
}

