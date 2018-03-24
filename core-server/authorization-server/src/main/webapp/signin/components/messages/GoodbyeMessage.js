// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {parse} from 'query-string';

class ShouldAddGoodbyeMessage extends Component<ShouldAddGoodbyeMessage.propTypes> {
  componentDidMount() {
    const {addFlashMessage, texts} = this.props;

    if (parse(window.location.search.substr(1)).goodbye !== undefined) {
      addFlashMessage({
        type: 'success',
        title: texts.logout_succeed_alert_title,
        text: texts.logout_succeed_alert_text
      });
    }
  }

  render() {
    return (null);
  }
}

ShouldAddGoodbyeMessage.propTypes = {
  addFlashMessage: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
}

export default ShouldAddGoodbyeMessage;