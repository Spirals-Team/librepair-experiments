// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';

import {isAuthenticated} from '../../actions/authActions';

import Navbar from './Navbar';
import FlashMessageList from '../common/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

class App extends Component<App.propTypes> {
  componentWillMount() {
    this.props.actions.isAuthenticated();
  }

  render() {
    return (
      <div>
        <Navbar/>

        <div className='container'>
          <FlashMessageList/>
          {this.props.children}
        </div>
      </div>
    );
  }
}

App.propTypes = {
  actions: PropTypes.object.isRequired
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      isAuthenticated: bindActionCreators(isAuthenticated, dispatch)
    }
  }
}

export default connect(null, mapDispatchToProps)(App);