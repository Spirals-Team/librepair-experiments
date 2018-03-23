// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import isEmpty from 'lodash/isEmpty';

import Table from 'react-bootstrap/lib/Table';

import CollapsableAlert from '../../../common/components/CollapsableAlert';

require('bootstrap/dist/css/bootstrap.css');

class ClientList extends Component<ClientList.propTypes> {
  render() {
    const {clients, texts} = this.props;

    if (isEmpty(clients)) {
      return (
        <CollapsableAlert collapse={true} style='warning' title={texts.errors.no_clients_alert_title}
                          message={texts.errors.no_clients_alert_text}/>
      );
    } else {
      return (
        <div>
          <Table hover responsive>
            <thead>
            {
              Object.keys(clients[0]).forEach(key => <th>key</th>)
            }
            </thead>
          </Table>
        </div>
      );
    }
  }
}

ClientList.propTypes = {
  clients: PropTypes.array.isRequired,
  texts: PropTypes.object.isRequired
}

export default ClientList;