// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import translateAuthenticationError from '../../utils/translateAuthenticationError';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup'
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

import CollapsableAlert from '../../../common/components/CollapsableAlert';

require('bootstrap/dist/css/bootstrap.css');

type UsernamePasswordSigninFormState = {
  username: string,
  password: string,
  errors: {
    username: string;
    password: string;
    form: string
  },
  isValid: boolean,
  isLoading: boolean
}

class UsernamePasswordSigninForm extends Component<UsernamePasswordSigninForm.propTypes, UsernamePasswordSigninFormState> {
  onChange: () => void;
  validateForm: () => void;
  onSubmit: () => void;

  constructor(props: UsernamePasswordSigninForm.propTypes) {
    super(props);

    this.state = {
      username: '',
      password: '',
      errors: {
        username: '',
        password: '',
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
    this.setState({isValid: this.state.username && this.state.password});
  }

  onSubmit(event: SyntheticInputEvent<HTMLInputElement>) {
    event.preventDefault();

    this.setState({errors: {form: ''}}, () => {
      this.props.signinUser(this.state).then(
        response => window.location.replace(response.headers['no-redirect'])
        , error => this.setState({errors: {form: translateAuthenticationError(error.response.data.message)}})
      );
    });
  }

  render() {
    const {isValid, isLoading, errors} = this.state;
    const {texts} = this.props;

    return (
      <Form onSubmit={this.onSubmit} horizontal>
        <CollapsableAlert style='danger' title={texts.errors.title} message={errors.form} collapse={!!errors.form}/>

        <FormGroup controlId='username' validationState={!!errors.username ? 'error' : null}>
          <HelpBlock>{errors.username}</HelpBlock>
          <Col componentClass={ControlLabel} sm={4}>
            {texts.username_form_control}
          </Col>
          <Col sm={6}>
            <FormControl name='username' type='text' value={this.state.username}
                         onChange={this.onChange} required/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup controlId='password' validationState={!!errors.password ? 'error' : null}>
          <HelpBlock>{errors.password}</HelpBlock>
          <Col componentClass={ControlLabel} sm={4}>
            {texts.password_form_control}
          </Col>
          <Col sm={6}>
            <FormControl name='password' type='password' value={this.state.password}
                         onChange={this.onChange} required/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup className='link-group'>
          <Col smOffset={4} sm={3}>
            <a href='signup' className='pull-left'>{texts.signup_link_text}</a>
          </Col>
          <Col sm={3}>
            <Button type='submit' bsStyle='primary' className='pull-right' disabled={!isValid || isLoading}
                    onClick={isValid && !isLoading ? this.onSubmit : null}>
              {isLoading ? texts.signin_button_loading_text : texts.signin_button_text}
            </Button>
          </Col>
        </FormGroup>
      </Form>
    );
  }
}

UsernamePasswordSigninForm.propTypes = {
  signinUser: PropTypes.func.isRequired,
  addFlashMessage: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
}

export default UsernamePasswordSigninForm;