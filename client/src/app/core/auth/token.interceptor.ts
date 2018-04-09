import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import 'rxjs/add/operator/do';
import { isNil } from 'lodash';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(public auth: AuthService,
              private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = AuthService.getToken();
    request = request.clone({
      setHeaders: {
        Authorization: isNil(token) ? '' : `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    });

    return next.handle(request);
  }
}
