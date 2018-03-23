'use strict';

import axios from 'axios/index';

import {REST_API_BASE_PATH} from '../config';

import {SET_CLIENTS} from './types';

export function setClients(clients) {
  return {
    type: SET_CLIENTS,
    clients
  }
}

export function loadClients() {
  return dispatch => {
    return axios.get(`${REST_API_BASE_PATH}/clients`).then(
      response => dispatch(setClients(response.data))
    );
  }
}

export function saveClient(client) {
  return {};
}