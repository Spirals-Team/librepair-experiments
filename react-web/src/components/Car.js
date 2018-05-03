import React, { Component } from 'react';
import '../styles/Car.css';
import CarInfo from './CarInfo';
import Booking from './Booking';
import App from './App';
import Main from './Main';

export default class Car extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showComponent: false, car: undefined
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

 test (data){
console.log("te");
this.setState({car: data})
  }

      
      render() {

    return (
      <div>
{this.state.car ? <App car={this.state.car}/> : 

      <div className="Car">
        <h3 className="Car-header">{this.props.car.category}</h3>
        <img className="Car-picture" alt="" src={this.props.car.picture}></img>
        <p className="Car-link">
          <a onClick={this._onButtonClick} href="">Details</a>
        </p>
        {this.state.showComponent ?
          <Main car={this.props.car} /> : null
        }
        <p className="Car-price">Price per day: {this.props.car.priceperday}</p>
        {/* <button className="Car-button" onClick={((e) => this.selectCar(this.props.car))} >Book Car [NYI]</button> */}
        <button className="Car-button" onClick={((e) =>this.test(this.props.car))} >Book Car [NYI]</button>
      </div>
    
}
</div>)
  }
  }


