'use strict';

import React from 'react';
import {render} from 'react-dom';

import {Provider} from 'react-redux';
import configureStore from './configureStore';

import {CookiesProvider} from 'react-cookie';

import App from './container/app';
import Authorize from './container/app/Authorize';

require('../common/styles/tbme-tv.css');

const store = configureStore();

render(
  <Provider store={store}>
    <CookiesProvider>
      <App>
        <Authorize/>
      </App>
    </CookiesProvider>
  </Provider>
  , (document.getElementById('app'): any));