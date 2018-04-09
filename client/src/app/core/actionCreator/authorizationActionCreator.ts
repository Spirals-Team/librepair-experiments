import { Injectable } from '@angular/core';
import { AuthorizationActions, ChangeAuthorizationStatusAction } from '../action/authorizationActions';
import { dispatch } from '@angular-redux/store';

@Injectable()
export class AuthorizationActionCreator {
  @dispatch()
  changeAuthorizationStatus(status: number): ChangeAuthorizationStatusAction {
    return {
      type: AuthorizationActions.ChangeAuthorizationStatus,
      status
    };
  }
}
