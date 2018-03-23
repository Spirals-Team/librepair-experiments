'use strict';

import {combineReducers} from 'redux';

import flashMessages from '../../common/reducers/flashMessages';
import language from './language';

export default combineReducers({
  flashMessages, language
});