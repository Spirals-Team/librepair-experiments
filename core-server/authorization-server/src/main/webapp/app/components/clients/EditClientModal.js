// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import join from 'lodash/join';

import Modal from 'react-bootstrap/lib/Modal';
import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup'
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

import CollapsableAlert from '../../../common/components/CollapsableAlert';
import InfiniteTextInputWrapper from '../common/InfiniteTextInputWrapper';

require('bootstrap/dist/css/bootstrap.css');

type EditClientModalState = {
  id: ?number,
  clientId: string,
  secret: string,
  accessTokenValidity: string,
  refreshTokenValidity: string,
  redirectUri: string,
  grantTypes: any[],
  authorities: any[],
  scopes: any[],
  errors: {
    clientId: string,
    secret: string,
    accessTokenValidity: string,
    refreshTokenValidity: string,
    redirectUri: string,
    form: string
  },
  isValid: boolean,
  isLoading: boolean
}

class EditClientModal extends Component<EditClientModal.propTypes, EditClientModalState> {
  onChange: () => void;
  validateForm: () => void;
  onSubmit: () => void;

  constructor(props: EditClientModal.propTypes) {
    super(props);

    this.state = {
      id: null,
      clientId: '',
      secret: '',
      accessTokenValidity: '',
      refreshTokenValidity: '',
      redirectUri: '',
      grantTypes: [],
      authorities: [],
      scopes: [],
      errors: {
        clientId: '',
        secret: '',
        accessTokenValidity: '',
        refreshTokenValidity: '',
        redirectUri: '',
        form: ''
      },
      isValid: false,
      isLoading: false
    }

    this.onChange = this.onChange.bind(this);
    this.validateForm = this.validateForm.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(event: SyntheticInputEvent<HTMLInputElement>) {
    this.setState({[event.target.name]: event.target.value}, this.validateForm);
  }

  validateForm() {
    // TODO
  }

  onSubmit(event: SyntheticInputEvent<HTMLInputElement>) {
    event.preventDefault();

    console.log(this.state);
  }

  render() {
    const {isValid, isLoading, errors} = this.state;
    const {texts} = this.props;

    return (
      <Modal.Dialog>
        <Modal.Header>
          <Modal.Title>{this.state.id ? texts.update_title : texts.create_title}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={this.onSubmit} horizontal>
            <CollapsableAlert style='danger'
                              title={this.state.id ? texts.errors.update_title : texts.errors.create_title}
                              message={errors.form} collapse={!!errors.form}/>

            <FormGroup controlId='clientId' validationState={!!errors.clientId ? 'error' : null}>
              <HelpBlock>{errors.clientId}</HelpBlock>
              <Col componentClass={ControlLabel} sm={4}>
                {texts.client_id}
              </Col>
              <Col sm={6}>
                <FormControl name='clientId' type='text' value={this.state.clientId}
                             onChange={this.onChange} required/>
                <FormControl.Feedback/>
              </Col>
            </FormGroup>

            <FormGroup controlId='secret' validationState={!!errors.secret ? 'error' : null}>
              <HelpBlock>{errors.secret}</HelpBlock>
              <Col componentClass={ControlLabel} sm={4}>
                {texts.secret}
              </Col>
              <Col sm={6}>
                <FormControl name='secret' type='password' value={this.state.secret}
                             onChange={this.onChange} required/>
                <FormControl.Feedback/>
              </Col>
            </FormGroup>

            <FormGroup controlId='accessTokenValidity' validationState={!!errors.accessTokenValidity ? 'error' : null}>
              <HelpBlock>{errors.accessTokenValidity}</HelpBlock>
              <Col componentClass={ControlLabel} sm={4}>
                {texts.access_token_validity}
              </Col>
              <Col sm={6}>
                <FormControl name='accessTokenValidity' type='number' value={this.state.accessTokenValidity}
                             onChange={this.onChange} required/>
                <FormControl.Feedback/>
              </Col>
            </FormGroup>

            <FormGroup controlId='refreshTokenValidity'
                       validationState={!!errors.refreshTokenValidity ? 'error' : null}>
              <HelpBlock>{errors.refreshTokenValidity}</HelpBlock>
              <Col componentClass={ControlLabel} sm={4}>
                {texts.refresh_token_validity}
              </Col>
              <Col sm={6}>
                <FormControl name='refreshTokenValidity' type='number' value={this.state.refreshTokenValidity}
                             onChange={this.onChange} required/>
                <FormControl.Feedback/>
              </Col>
            </FormGroup>

            <InfiniteTextInputWrapper controlId='redirectUris' validationState={this.state.errors.redirectUri}
                                      inputName={texts.redirect_uris}
                                      concatenateTextValues={(valueArray) => join(valueArray, ';')}
                                      setConcatenatedValue={(value) => this.setState({redirectUri: value})}/>

            <FormGroup className='link-group'>
              <Col smOffset={7} sm={3}>
                <Button type='submit' bsStyle='primary' className='pull-right'
                  // disabled={!isValid || isLoading} onClick={isValid && !isLoading ? this.onSubmit : null}
                >
                  {this.state.id && !isLoading ? texts.update_button_text :
                    !this.state.id && !isLoading ? texts.create_button_text : texts.button_loading_text}
                </Button>
              </Col>
            </FormGroup>
            {/*</Modal.Footer>*/}
          </Form>
        </Modal.Body>
      </Modal.Dialog>
    );
  }
}

EditClientModal.propTypes = {
  addFlashMessage: PropTypes.func.isRequired,
  saveClient: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
}

export default EditClientModal;