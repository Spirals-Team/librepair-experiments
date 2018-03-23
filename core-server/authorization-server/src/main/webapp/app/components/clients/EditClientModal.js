// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import Modal from 'react-bootstrap/lib/Modal';
import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup'
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

import CollapsableAlert from '../../../common/components/CollapsableAlert';

require('bootstrap/dist/css/bootstrap.css');

class EditClientModal extends Component<EditClientModal.propTypes> {
  onChange: () => void;
  validateForm: () => void;
  addRedirectUri: () => void;
  onSubmit: () => void;

  constructor(props) {
    super(props);

    this.state = {
      id: null,
      clientId: '',
      secret: '',
      accessTokenValidity: '',
      refreshTokenValidity: '',
      redirectUris: [''],
      grantTypes: [],
      authorities: [],
      scopes: [],
      errors: {
        clientId: '',
        secret: '',
        accessTokenValidity: '',
        refreshTokenValidity: '',
        redirectUris: ''
      },
      isValid: false,
      isLoading: false
    }

    this.onChange = this.onChange.bind(this);
    this.validateForm = this.validateForm.bind(this);
    this.addRedirectUri = this.addRedirectUri.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(event: SyntheticInputEvent<HTMLInputElement>) {
    this.setState({[event.target.name]: event.target.value}, this.validateForm);
  }

  validateForm() {
    this.setState({isValid: this.state.username && this.state.password});
  }

  addRedirectUri() {
    const {redirectUris} = this.state;
    redirectUris.push('');

    this.setState({redirectUris: redirectUris}, () => {
      console.log(this.state);
    });
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

            <FormGroup controlId='redirectUris' validationState={!!errors.redirectUris ? 'error' : null}>
              <HelpBlock>{errors.redirectUris}</HelpBlock>
              <Col componentClass={ControlLabel} sm={4}>
                {texts.redirect_uris}
              </Col>

              {this.state.redirectUris.map((redirectUri, index) => {
                const id = 'redirectUri-' + index;

                if (index === 0) {
                  return (
                    <Col key={id} sm={4}>
                      <FormControl name={id} type='text' value={redirectUri}
                                   onChange={this.onChange} required/>
                      <FormControl.Feedback/>
                    </Col>
                  );
                }

                return (
                  <Col key={id} smOffset={4} sm={4} className='continuous-input-offset-top'>
                    <FormControl name={id} type='text' value={redirectUri}
                                 onChange={this.onChange} required/>
                    <FormControl.Feedback/>
                  </Col>
                );
              })}

              <Col sm={2}>
                <Button bsStyle='primary' className='pull-right' onClick={this.addRedirectUri}>+</Button>
              </Col>
            </FormGroup>

            <FormGroup className='link-group'>
              <Col smOffset={7} sm={3}>
                <Button type='submit' bsStyle='primary' className='pull-right' disabled={!isValid || isLoading}
                        onClick={isValid && !isLoading ? this.onSubmit : null}>
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