import { Action } from 'redux';

export enum AuthorizationActions {
  ChangeAuthorizationStatus = 'CHANGE_AUTHORIZATION_STATUS'
}

export interface ChangeAuthorizationStatusAction extends Action {
  status: number;
}
