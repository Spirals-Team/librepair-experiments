'use strict';

import axios from 'axios';

import {REST_API_BASE_PATH} from '../config';

export function loadScopes() {
  return axios.get(`${REST_API_BASE_PATH}/scopes`);
}