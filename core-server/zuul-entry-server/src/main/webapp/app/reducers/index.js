'use strict';

import {combineReducers} from 'redux';

import auth from './auth';
import flashMessages from './flashMessages';
import language from './language';

export default combineReducers({
  auth, flashMessages, language
});