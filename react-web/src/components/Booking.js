import React from 'react';
import '../styles/Car.css';
import facade from '../Facade';

export default class Booking extends React.Component {

    constructor(props) {
        super(props)

        this.regno = props.match.params.car;

        this.book = this.book.bind(this);

        const now = new Date();
        const day = ('0' + now.getDate());
        const month = ('0' + now.getMonth());
        const year = now.getFullYear();

        this.state = {
            car: {},
            date: year + '-' + month + '-' + day,
            succes: false
        }
    }

    handleChange = (event) => {
        this.setState({ [event.target.id]: event.target.value });
    }

    handleSubmit(event) {
        alert('submitted: ' + this.state.value);
        event.preventDefault();
    }

    book() {
        console.log(this.state)
        if (this.state.email === undefined || this.state.datestart === undefined || this.state.dateend === undefined) {
            alert("please specify an email, startdate and enddate")
        } else
            this.setState({ succes: true });
    }

    async componentDidMount() {
        const car = await facade.fetchSpecCar(this.regno);
        this.setState({ car: car[0] });
    }


    render() {
        return (
            <div>

                <p><img className="logo-mini" alt="" src={this.state.car.picture}></img></p>
                {/* <p><img className="logo-mini" alt="" src={this.props.car.logo}></img></p> */}
                <p>Company: {this.state.car.company}</p>
                <p>category: {this.state.car.category}</p>
                <p>make: {this.state.car.make}</p>
                <p>model: {this.state.car.model}</p>
                <p>year: {this.state.car.year}</p>
                <p>regno: {this.state.car.regno}</p>
                <p>seats: {this.state.car.seats}</p>
                <p>doors: {this.state.car.doors}</p>
                <p>gear: {this.state.car.gear}</p>
                <p>aircondition: {this.state.car.aircondition}</p>
                <p>location: {this.state.car.location}</p>
                <p>priceperday: {this.state.car.priceperday}</p>

                {this.state.succes ?
                    <div>
                        <h2>Car Booked! [NOT FUNCTIONAL YET]</h2>
                        <p>email: {this.state.email}</p>
                        <p>Start date: {this.state.datestart}</p>
                        <p>End date: {this.state.dateend}</p>
                    </div>
                    :
                    <div>
                        <h2>Book car</h2>
                        <p>Your email: <input type="text" name="email" id="email" onChange={this.handleChange} /></p>
                        <p>Start date: <input type="date" name="datestart" id="datestart" value={this.state.date1} onChange={this.handleChange} /></p>
                        <p>End date: <input type="date" name="dateend" id="dateend" value={this.state.date2} onChange={this.handleChange} /></p>
                        <button type="button" onClick={this.book} className="confirm-booking" >Accept Booking [not yet implemented]</button>
                    </div>
                }

            </div>

        );

    }

}

















