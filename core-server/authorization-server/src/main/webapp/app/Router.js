// @flow
'use strict';

import React, {Component} from 'react';

import {HashRouter, Route, Switch} from 'react-router-dom';

import accessWithAuthority from './utils/accessWithAuthority';

import {SERVER_ADMIN} from '../common/contants/AuthorityConstants';

import App from './container/app';
import Profile from './container/app/Profile';

class Router extends Component<Router.propTypes> {
  render() {
    return (
      <HashRouter>
        <Switch>
          <App>
            <Route path='/clients' component={accessWithAuthority(Profile, SERVER_ADMIN)}/>
            <Route path='/profile' component={Profile}/>
          </App>
        </Switch>
      </HashRouter>
    );
  }
}

export default Router;