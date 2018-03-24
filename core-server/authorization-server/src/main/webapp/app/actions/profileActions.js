'use strict';

import axios from 'axios/index';

import {SET_PROFILE} from './types';

export function setProfile(profile) {
  return {
    type: SET_PROFILE,
    profile
  }
}

export function loadProfile() {
  return dispatch => {
    return axios.get('profile').then(
      response => dispatch(setProfile(response.data))
    );
  }
}