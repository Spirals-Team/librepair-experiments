// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {parse} from 'query-string';

class ConfirmationMessage extends Component<ConfirmationMessage.propTypes> {
  componentDidMount() {
    const {addFlashMessage, texts} = this.props;

    if (parse(window.location.search.substr(1)).confirmation_failed !== undefined) {
      addFlashMessage({
        type: 'danger',
        title: texts.confirmation_failed_alert_title,
        text: texts.confirmation_failed_alert_text
      });
    } else if (parse(window.location.search.substr(1)).confirmation_succeed !== undefined) {
      addFlashMessage({
        type: 'success',
        title: texts.confirmation_succeed_alert_title,
        text: texts.confirmation_succeed_alert_text
      });
    }
  }

  render() {
    return (null);
  }
}

ConfirmationMessage.propTypes = {
  addFlashMessage: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
}

export default ConfirmationMessage;