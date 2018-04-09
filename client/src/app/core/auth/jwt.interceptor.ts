import {
  HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest,
  HttpResponse
} from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { authorizationPath } from '../paths/AuthorizationPaths';
import { NgRedux } from '@angular-redux/store';
import { IGlobalState } from '../state/globalState';
import { AuthorizationActionCreator } from '../actionCreator/authorizationActionCreator';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private auth: AuthService,
              private ngRedux: NgRedux<IGlobalState>,
              private actions: AuthorizationActionCreator,
              private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(request).do((event: HttpEvent<any>) => {
      if (event instanceof HttpResponse) {
        if (event.url === authorizationPath) {
          this.actions.changeAuthorizationStatus(event.status);
        }
      }
    }, (err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (err.url === authorizationPath) {
          this.actions.changeAuthorizationStatus(err.status);
        }
      }
    });
  }
}
