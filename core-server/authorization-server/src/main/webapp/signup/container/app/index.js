// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import SignupJumbotron from '../../components/signup/SignupJumbotron';
import FlashMessageList from '../common/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

class App extends Component<App.propTypes> {
  render() {
    const {texts} = this.props;

    return (
      <div className='container'>
        <SignupJumbotron texts={texts}/>
        <FlashMessageList/>
        {this.props.children}
      </div>
    );
  }
}

App.propTypes = {
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    texts: state.language.texts.app
  }
}

export default connect(mapStateToProps)(App);