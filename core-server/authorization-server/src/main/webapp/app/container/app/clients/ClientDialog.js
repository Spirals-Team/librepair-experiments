// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

import {saveClient} from '../../../actions/clientActions';
import {addFlashMessage} from '../../../../common/actions/flashMessageActions';

import EditClientModal from '../../../components/clients/EditClientModal';

class ClientDialog extends Component<ClientDialog.propTypes> {
  render() {
    const {texts} = this.props;
    const {addFlashMessage, saveClient} = this.props.actions;

    return (
      <EditClientModal addFlashMessage={addFlashMessage} saveClient={saveClient} texts={texts.modal}/>
    );
  }
}

ClientDialog.propTypes = {
  actions: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    texts: state.language.texts.clients
  }
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      addFlashMessage: bindActionCreators(addFlashMessage, dispatch),
      saveClient: bindActionCreators(saveClient, dispatch)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(ClientDialog);