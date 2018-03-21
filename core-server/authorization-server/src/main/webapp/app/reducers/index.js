'use strict';

import {combineReducers} from 'redux';

import flashMessages from '../../common/reducers/flashMessages';
import language from './language';
import profile from './profile';

export default combineReducers({
  flashMessages, language, profile
});