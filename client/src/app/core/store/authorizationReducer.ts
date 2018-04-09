import { Action } from 'redux';
import { IAuthorizationState } from '../state/authorizationState';
import { AuthorizationActions, ChangeAuthorizationStatusAction } from '../action/authorizationActions';
import { createNextState } from '../helpers/createNextState';

export const INITIAL_STATE: IAuthorizationState = {
  authorizationStatus: undefined,
};

const changeAuthorizationState = (state: IAuthorizationState, action: ChangeAuthorizationStatusAction) => {
  return createNextState(state, { authorizationStatus: action.status });
};

export function createAuthorizationReducer(state: IAuthorizationState = INITIAL_STATE, action: Action): IAuthorizationState {
  switch (action.type) {
    case AuthorizationActions.ChangeAuthorizationStatus:
      return changeAuthorizationState(state, action as ChangeAuthorizationStatusAction);
    default:
      return state;
  }
}
