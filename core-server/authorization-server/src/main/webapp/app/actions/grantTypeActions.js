'use strict';

import axios from 'axios';

import {REST_API_BASE_PATH} from '../config';

export function loadGrantTypes() {
  return axios.get(`${REST_API_BASE_PATH}/grant-types`);
}