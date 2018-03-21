// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {parse} from 'query-string';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup'
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import Radio from 'react-bootstrap/lib/Radio';
import Button from 'react-bootstrap/lib/Button';

import CollapsableAlert from '../../../common/components/CollapsableAlert';

require('bootstrap/dist/css/bootstrap.css');

const CLIENT_PLACEHOLDER = '[CLIENT_ID]';

type ClientApprovalFormState = {
  clientId: string,
  scopes: string[],
  errors: { form: string }
};

class ClientApprovalForm extends Component<ClientApprovalForm.propTypes, ClientApprovalFormState> {
  constructor(props: ClientApprovalForm.propTypes) {
    super(props);

    this.state = {
      clientId: '',
      scopes: [],
      errors: {form: ''}
    }
  }

  componentWillMount() {
    this.setState({clientId: parse(window.location.search.substr(1)).client_id}, () => {
      this.props.getClientApprovals(this.state.clientId).then(
        response => this.setState({scopes: response.data})
        , error => this.setState({errors: {form: error.response.data.message}})
      );
    });
  }

  render() {
    const {scopes, errors} = this.state;
    const {texts} = this.props;

    return (
      <Form id='confirmationForm' name='confirmationForm' action='authorize' method='post' horizontal>
        {/*TODO: ClientId is not the most beautiful thing here.. maybe add client name?*/}
        <h1>{texts.approve_title_question.replace(CLIENT_PLACEHOLDER, '\'' + this.state.clientId + '\'')}</h1>

        <CollapsableAlert style='danger' title={texts.scope_fetch_failed_alert_title}
                          message={texts.scope_fetch_failed_alert_text}
                          collapse={!!errors.form}/>

        <FormControl name='user_oauth_approval' value='true' type='hidden'/>

        {
          scopes.map(scope => {
            return (
              <FormGroup key={scope}>
                <Col componentClass={ControlLabel} sm={4}>
                  {scope}
                </Col>
                <Col sm={8}>
                  <Radio name={'scope.' + scope} value='true' inline defaultChecked>
                    {texts.toggle_approve}
                  </Radio>
                  <Radio name={'scope.' + scope} value='false' inline>
                    {texts.toggle_deny}
                  </Radio>
                </Col>
              </FormGroup>
            );
          })
        }

        <FormGroup className='link-group'>
          <Col smOffset={8} sm={4}>
            <Button name='authorize' value='Authorize' type='submit' bsStyle='primary' className='pull-right'>
              {texts.approve_button_text}
            </Button>
          </Col>
        </FormGroup>
      </Form>
    );
  }
}

ClientApprovalForm.propTypes = {
  getClientApprovals: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
}

export default ClientApprovalForm;