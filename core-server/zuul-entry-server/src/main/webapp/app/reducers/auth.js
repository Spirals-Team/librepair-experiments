'use strict';

import {AUTHENTICATE_USER} from '../actions/types';

const initialState = {
  isAuthenticated: false
};

export default (state = initialState, action = {}) => {
  switch (action.type) {
    case AUTHENTICATE_USER:
      return {
        isAuthenticated: action.state
      };
    default:
      return state;
  }
}