'use strict';

import {SET_CLIENTS} from '../actions/types';

const initialState = {};

export default (state = initialState, action = {}) => {
  switch (action.type) {
    case SET_CLIENTS:
      return action.clients;
    default:
      return state;
  }
}