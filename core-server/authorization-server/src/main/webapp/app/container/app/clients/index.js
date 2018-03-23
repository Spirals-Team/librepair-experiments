// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {Link} from 'react-router-dom';

import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

import {loadClients} from '../../../actions/clientActions';
import {addFlashMessage} from '../../../../common/actions/flashMessageActions';

import PageHeader from 'react-bootstrap/lib/PageHeader';
import Row from 'react-bootstrap/lib/Row';
import Button from 'react-bootstrap/lib/Button';

import ClientList from '../../../components/clients/ClientList';

require('../../../styles/clients.css');
require('bootstrap/dist/css/bootstrap.css');

class Clients extends Component <Clients.propTypes> {
  componentWillMount() {
    const {texts} = this.props;
    const {addFlashMessage, loadClients} = this.props.actions;

    loadClients().then(
      response => {
      },
      error => addFlashMessage({
        type: 'danger',
        title: texts.errors.clients_fetch_failed_alert_title,
        text: texts.errors.clients_fetch_failed_alert_text
      })
    )
  }

  render() {
    const {clients, texts} = this.props;

    return (
      <div>
        <PageHeader>{texts.title}</PageHeader>

        <Row>
          <Link to='/clients/new'>
            <Button bsStyle='primary' className='pull-right'>{texts.create_client_button}</Button>
          </Link>
        </Row>

        <Row>
          <ClientList clients={clients} texts={texts}/>
        </Row>
      </div>
    );
  }
}

Clients.propTypes = {
  actions: PropTypes.object.isRequired,
  clients: PropTypes.array.isRequired,
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    clients: state.clients,
    texts: state.language.texts.clients
  }
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      addFlashMessage: bindActionCreators(addFlashMessage, dispatch),
      loadClients: bindActionCreators(loadClients, dispatch)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Clients);