// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {login, logout} from '../../actions/authActions';

import Navigation from '../../components/Navigation';

class Navbar extends Component<Navbar.propTypes> {
  render() {
    const {auth, texts} = this.props;

    return (
      <div>
        <Navigation login={login} logout={logout} isAuthenticated={auth.isAuthenticated} texts={texts}/>
      </div>
    );
  }
}

Navbar.propTypes = {
  auth: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    auth: state.auth,
    texts: state.language.texts.navbar
  }
}

export default connect(mapStateToProps)(Navbar);