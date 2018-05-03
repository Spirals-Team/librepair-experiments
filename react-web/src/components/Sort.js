import React, { Component } from 'react';

export default class Sort extends Component {

  render() {
    return (
      <div>
        <button type="button" onClick={this.props.sortingSwitch}>Sort by Price</button>
      </div>
    )
  }
};