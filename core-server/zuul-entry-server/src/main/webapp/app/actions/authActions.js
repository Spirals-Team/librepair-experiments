'use strict';

import {AUTHENTICATE_USER} from './types';

import axios from 'axios';

export function setAuthenticationState(state) {
  return {
    type: AUTHENTICATE_USER,
    state
  };
}

export function login() {
  window.location.replace('login');
}

export function logout() {
  window.location.replace('logout');
}

export function isAuthenticated() {
  return dispatch => {
    return axios.get('authenticated').then(
      response => dispatch(setAuthenticationState(response.data))
    );
  };
}