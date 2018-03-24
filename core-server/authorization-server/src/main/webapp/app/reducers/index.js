'use strict';

import {combineReducers} from 'redux';

import clients from './clients';
import flashMessages from '../../common/reducers/flashMessages';
import language from './language';
import profile from './profile';

export default combineReducers({
  clients, flashMessages, language, profile
});