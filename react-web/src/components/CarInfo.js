import React, { Component } from 'react';
import '../styles/Car.css';

export default class CarInfo extends Component {

    render() {
        return (
            <div className="car-data">
                <p><img className="logo-mini" alt="" src={this.props.car.logo}></img></p>
                <p>Company: {this.props.car.company}</p>
                {/* <p>category: {this.props.carJSON.category}</p> */}
                <p>make: {this.props.car.make}</p>
                <p>model: {this.props.car.model}</p>
                <p>year: {this.props.car.year}</p>
                <p>regno: {this.props.car.regno}</p>
                <p>seats: {this.props.car.seats}</p>
                <p>doors: {this.props.car.doors}</p>
                <p>gear: {this.props.car.gear}</p>
                <p>aircondition: {this.props.car.aircondition.toString()}</p>
                <p>location: {this.props.car.location}</p>
                {/* <p>priceperday: {this.props.carJSON.priceperday}</p> */}
            </div>
        );
    }
}



