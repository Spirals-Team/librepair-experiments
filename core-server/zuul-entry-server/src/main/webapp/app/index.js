// @flow
'use strict';

import React from 'react';
import {render} from 'react-dom';

import {Provider} from 'react-redux';
import configureStore from './configureStore';

import {CookiesProvider} from 'react-cookie';

import Router from './Router';

require('./styles/tbme-tv.css');

const store = configureStore();

render(
  <Provider store={store}>
    <CookiesProvider>
      <Router/>
    </CookiesProvider>
  </Provider>
  , (document.getElementById('app'): any));