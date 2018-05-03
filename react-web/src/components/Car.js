import React, { Component } from 'react';
import '../styles/Car.css';
import CarInfo from './CarInfo';

export default class Car extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showComponent: false,
    }
    this._onButtonClick = this._onButtonClick.bind(this);
  }


  _onButtonClick(event) {
    event.preventDefault();
    if (this.state.showComponent === true) {
      this.setState({
        showComponent: false,
      });
    } else {
      this.setState({
        showComponent: true,
      });
    }
  }

  render() {

    return (
      <div className="Car">
        <h3 className="Car-header">{this.props.car.category}</h3>
        <img className="Car-picture" alt="" src={this.props.car.picture}></img>
        <p className="Car-link">
          <a onClick={this._onButtonClick} href="">Details</a>
        </p>
        {this.state.showComponent ?
          <CarInfo car={this.props.car} /> : null
        }
        <p className="Car-price">Price per day: {this.props.car.priceperday}</p>
        <button className="Car-button">Book Car [NYI]</button>


      </div>
    );
  }
}



