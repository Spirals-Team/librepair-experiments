import { composeReducers, defaultFormReducer } from '@angular-redux/form';
import { combineReducers } from 'redux';
import { createAuthorizationReducer } from './authorizationReducer';
import { ReduxFormsNames } from '../state/ReduxFormsNames';

export const rootReducer = composeReducers(
  defaultFormReducer(),
  combineReducers({
    auth: createAuthorizationReducer,
    [ReduxFormsNames.authorizationForm]: (state = {}) => state,
    [ReduxFormsNames.registrationForm]: (state = {}) => state,
  })
);
