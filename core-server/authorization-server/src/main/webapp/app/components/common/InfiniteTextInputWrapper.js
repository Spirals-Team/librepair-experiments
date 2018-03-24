// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import shortid from 'shortid';

import FormGroup from 'react-bootstrap/lib/FormGroup'
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

require('bootstrap/dist/css/bootstrap.css');

const SPLITTERATOR = '-';

let lastFocused = 0;
let nextFocus = '';

type InfiniteInputWrapperState = {
  values: string[]
}

class InfiniteInputWrapper extends Component<InfiniteInputWrapper.propTypes, InfiniteInputWrapperState> {
  onFocus: () => void;
  onChange: () => void;
  addNewEmptyValue: () => void;

  constructor(props: InfiniteInputWrapper.propTypes) {
    super(props);

    this.state = {
      values: ['']
    }

    this.onFocus = this.onFocus.bind(this);
    this.onChange = this.onChange.bind(this);
    this.addNewEmptyValue = this.addNewEmptyValue.bind(this);
  }

  onFocus(event: SyntheticInputEvent<HTMLInputElement>) {
    const index = this.extractIndex(event.target.name);
    lastFocused = index;

    let tmpValue = event.target.value;
    event.target.value = '';
    event.target.value = tmpValue;
  }

  onChange(event: SyntheticInputEvent<HTMLInputElement>) {
    const {values} = this.state;

    // $FlowFixMe: Dirty string array index as number
    values[this.extractIndex(event.target.name)] = event.target.value;

    this.setState({values: values}, () => this.props.setConcatenatedValue(this.props.concatenateTextValues(this.state.values)));
  }

  extractIndex(concatenatedKey: string) {
    return concatenatedKey.split(SPLITTERATOR)[1];
  }

  addNewEmptyValue() {
    const {values} = this.state;
    values.push('');

    this.setState({values: values});
  }

  componentDidUpdate() {
    // TODO: Whoo.. this is dirty!
    let possibleFocusElement;
    if ((possibleFocusElement = document.getElementsByName(nextFocus)[0]) != null) {
      possibleFocusElement.focus();
    }
  }

  render() {
    const {controlId, validationState, inputName} = this.props;
    const continuousButtonRowOffset = this.state.values.length > 1 ? 'continuous-input-offset-top' : '';

    const shortId = shortid.generate();
    nextFocus = shortId + SPLITTERATOR + lastFocused;

    return (
      <FormGroup controlId={controlId} validationState={!!validationState ? 'error' : null}>
        <HelpBlock>{validationState}</HelpBlock>
        <Col componentClass={ControlLabel} sm={4}>{inputName}</Col>

        {this.state.values.map((value, index) => {
          const id = shortId + SPLITTERATOR + index;

          if (index === 0) {
            return (
              <Col key={id} sm={4}>
                <FormControl name={id} type='text' value={value} onFocus={this.onFocus} onChange={this.onChange}
                             required/>
                <FormControl.Feedback/>
              </Col>
            );
          }

          return (
            <Col key={id} smOffset={4} sm={4} className='continuous-input-offset-top'>
              <FormControl name={id} type='text' value={value} onFocus={this.onFocus} onChange={this.onChange}
                           required/>
              <FormControl.Feedback/>
            </Col>
          );
        })}

        <Col sm={2} className={continuousButtonRowOffset}>
          <Button bsStyle='primary' className='pull-right' onClick={this.addNewEmptyValue}>+</Button>
        </Col>
      </FormGroup>
    );
  }
}

InfiniteInputWrapper.propTypes = {
  controlId: PropTypes.string.isRequired,
  validationState: PropTypes.string.isRequired,
  inputName: PropTypes.string.isRequired,
  concatenateTextValues: PropTypes.func.isRequired,
  setConcatenatedValue: PropTypes.func.isRequired
}

export default InfiniteInputWrapper;