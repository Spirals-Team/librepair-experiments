'use strict';

import {SET_LANGUAGE} from './types';

export function setCurrentLanguage(language) {
  return {
    type: SET_LANGUAGE,
    language
  }
}