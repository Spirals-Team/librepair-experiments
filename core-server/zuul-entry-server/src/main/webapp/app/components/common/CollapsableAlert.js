// @flow
'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import Collapse from 'react-bootstrap/lib/Collapse';
import Alert from 'react-bootstrap/lib/Alert';

require('bootstrap/dist/css/bootstrap.css');

class CollapsableAlert extends React.Component<CollapsableAlert.propTypes> {
  render() {
    return (
      <Collapse in={this.props.collapse}>
        <Alert bsStyle={this.props.style}>
          <strong>{this.props.title}</strong>
          {this.props.message}
          {this.props.children}
        </Alert>
      </Collapse>
    );
  }
}

CollapsableAlert.propTypes = {
  collapse: PropTypes.bool.isRequired,
  style: PropTypes.string.isRequired,
  title: PropTypes.string,
  message: PropTypes.string
}

export default CollapsableAlert;