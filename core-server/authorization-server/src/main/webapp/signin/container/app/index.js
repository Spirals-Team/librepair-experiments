// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

import {addFlashMessage} from "../../../common/actions/flashMessageActions";

import SigninJumbotron from "../../components/signin/SigninJumbotron";
import GoodbyeMessage from '../../components/messages/GoodbyeMessage';
import ConfirmationMessage from '../../components/messages/ConfirmationMessage';
import FlashMessageList from '../common/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

class App extends Component<App.propTypes> {
  render() {
    const {texts} = this.props;
    const {addFlashMessage} = this.props.actions;

    return (
      <div className='container'>
        <SigninJumbotron texts={texts}/>

        <GoodbyeMessage addFlashMessage={addFlashMessage} texts={texts.flash_messages.logout}/>
        <ConfirmationMessage addFlashMessage={addFlashMessage} texts={texts.flash_messages.confirmation}/>

        <FlashMessageList/>

        {this.props.children}
      </div>
    );
  }
}

App.propTypes = {
  actions: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    texts: state.language.texts.app
  }
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      addFlashMessage: bindActionCreators(addFlashMessage, dispatch)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(App);