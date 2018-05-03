import React, { Component } from 'react';
import '../styles/App.css';

export default class Home extends Component {
  render() {
    return (
      <div className="grid-container-home">
        <div className="grid-item">
          1
        </div>
        <div className="grid-item flex-container-centered">
          <div class="aligner-item"> Centered </div>
        </div>
      </div>
    );
  }
}
