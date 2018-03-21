// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import PageHeader from 'react-bootstrap/lib/PageHeader';
import Grid from 'react-bootstrap/lib/Grid';
import Row from 'react-bootstrap/lib/Row';
import Col from 'react-bootstrap/lib/Col';

require('../../styles/account-information.css');
require('bootstrap/dist/css/bootstrap.css');

class AccountInformation extends Component<AccountInformation.propTypes> {
  render() {
    const {account, texts} = this.props;
    const createdDate = new Date(account.created).toDateString();

    return (
      <div>
        <PageHeader>{texts.title}</PageHeader>

        <Grid>
          <Row>
            <Col className='text-heavy' sm={4} smOffset={2}>{texts.information.created}</Col>
            <Col sm={4}>{createdDate ? createdDate : null}</Col>
          </Row>
          <Row>
            <Col className='text-heavy' sm={4} smOffset={2}>{texts.information.username}</Col>
            <Col sm={4}>{account.username}</Col>
          </Row>
          <Row>
            <Col className='text-heavy' sm={4} smOffset={2}>{texts.information.email}</Col>
            <Col sm={4}>{account.email}</Col>
          </Row>
        </Grid>
      </div>
    );
  }
}

AccountInformation.propTypes = {
  account: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired,
}

export default AccountInformation;