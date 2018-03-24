'use strict';

import axios from 'axios';

import {REST_API_BASE_PATH} from '../config';

export function loadAuthorities() {
  return axios.get(`${REST_API_BASE_PATH}/authorities`);
}