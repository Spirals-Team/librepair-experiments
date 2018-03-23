// @flow
'use strict';

import React, {Component} from 'react';

import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

class Users extends Component {
  render() {
    return (<h1>This is gonna be a user management..</h1>);
  }
}

export default connect()(Users);