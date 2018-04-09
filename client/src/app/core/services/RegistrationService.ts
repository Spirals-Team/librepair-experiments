import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { ErrorHandler } from '../helpers/ErrorHandler';
import { catchError } from 'rxjs/operators';
import { registrationPath } from '../paths/AuthorizationPaths';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class RegistrationService {
  constructor(private http: HttpClient) {
  }

  public registryUser(username: string, password: string, firstname: string, lastname: string): Observable<boolean> {
    return this.http.post(registrationPath, { username, password, firstname, lastname })
      .pipe(
        catchError(ErrorHandler.handleError)
      );
  }
}
