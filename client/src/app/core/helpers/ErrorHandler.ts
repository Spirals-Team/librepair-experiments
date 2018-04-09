import { ErrorObservable } from 'rxjs/observable/ErrorObservable';
import { HttpErrorResponse } from '@angular/common/http';

export class ErrorHandler {
  static handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.warn('An error occurred by client side:', error.error.message);
    } else {
      console.warn(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // return an ErrorObservable with a user-facing error message
    return new ErrorObservable(
      'Something bad happened; please try again later.');
  }
}
