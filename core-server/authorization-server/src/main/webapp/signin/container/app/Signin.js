// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

import {addFlashMessage} from '../../../common/actions/flashMessageActions';
import signin from '../../utils/signin';

import UsernamePasswordSigninForm from '../../components/signin/UsernamePasswordSigninForm';

require('../../styles/signin.css');

class Signup extends Component<Signup.propTypes> {
  render() {
    const {texts} = this.props;
    const {addFlashMessage} = this.props.actions;

    return (
      <div>
        <div className='signin-form'>
          <UsernamePasswordSigninForm signinUser={signin} addFlashMessage={addFlashMessage} texts={texts}/>
        </div>
      </div>
    );
  }
}

Signup.propTypes = {
  actions: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    texts: state.language.texts.signin
  }
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      addFlashMessage: bindActionCreators(addFlashMessage, dispatch)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Signup);