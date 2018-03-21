// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

import {deleteFlashMessage} from '../../../common/actions/flashMessageActions';

import FlashMessage from '../../../common/components/FlashMessage';

class FlashMessagesList extends Component<FlashMessagesList.propTypes> {
  render() {
    const {messages} = this.props;
    const {deleteFlashMessage} = this.props.actions;

    return (
      <div>
        {
          messages.map(message =>
            <FlashMessage key={message.id} message={message} deleteFlashMessage={deleteFlashMessage}/>
          )
        }
      </div>
    );
  }
}

FlashMessagesList.propTypes = {
  messages: PropTypes.array.isRequired,
  actions: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    messages: state.flashMessages
  }
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      deleteFlashMessage: bindActionCreators(deleteFlashMessage, dispatch)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(FlashMessagesList);