// @flow
'use strict';

import React, {Component} from 'react';

import {Route, Switch, HashRouter} from 'react-router-dom';

import App from './container/app';

class Router extends Component<Router.propTypes> {
  render() {
    return (
      <HashRouter>
        <Switch>
          <App>
          </App>
        </Switch>
      </HashRouter>
    );
  }
}

export default Router;