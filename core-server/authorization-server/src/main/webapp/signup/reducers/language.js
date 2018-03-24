'use strict';

import {SET_LANGUAGE} from '../../common/actions/types';

const en = require('../config/languages/en.json');

const initialState = {
  name: en.language,
  texts: en,
  id: 'en'
}

export default (state = initialState, action = {}) => {
  switch (action.type) {
    case SET_LANGUAGE:
      try {
        const language = require('../config/languages/' + action.language + '.json');

        return {
          name: language.name,
          texts: language,
          id: action.language
        }
      } catch (error) {
        return state;
      }
    default:
      return state;
  }
}