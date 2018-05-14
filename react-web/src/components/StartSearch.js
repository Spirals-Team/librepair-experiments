import React, { Component } from 'react';
import { Link } from 'react-router-dom'
import '../styles/App.css';


const categories = ['luxury', 'mini elite', 'economy', 'standard', 'Premium'];
const locations = [
    'Cph (Copenhagen Airport)',
    'Billund Lufthavn',
    'Aalborg Lufthavn', ,
    'Copenhagen City',
    'Aarhus City',
    'Odense',
    'Herning',
    'Roskilde',
    'Esbjerg',
    'Naestved'
];

export default class StartSearch extends Component {
    constructor(props, context) {
        super(props, context)

        this.state = {
            category: '',
            location: '',
            fromdate: '',
            todate: '',
        }
    }

    dateCheck = (car) => {
        let isMatch = true;
        if (this.state.fromdate.length > 0 && this.state.todate.length > 0) {
            const prevenEvents = car.reservations.filter(reservation => {
                let isOK = true;

                let resFromDate = new Date(reservation.fromdate);
                let resToDate = new Date(reservation.todate);
                let seaFromDate = new Date(this.state.fromdate);
                let seaToDate = new Date(this.state.todate);

                if (seaFromDate > resFromDate && seaFromDate < resToDate) isOK = false;
                if (seaToDate > resFromDate && seaToDate < resToDate) isOK = false;
                if (seaFromDate < resFromDate && seaToDate > resToDate) isOK = false;

                return isOK;
            })

            if (prevenEvents.length > 0) isMatch = false;
        }
        return isMatch;
    }

    locationCheck = (car) => {
        let isMatch = true;
        if (this.state.location.length > 0) return (
            isMatch = this.state.location.toLocaleLowerCase() === car.location.toLocaleLowerCase()
        )
        return isMatch;
    }

    handleChange = (event) => {
        this.setState({ [event.target.id]: event.target.value });
    }

    searchFilter = (list) => {
        const res = list.filter(car => {
            return (
                this.locationCheck(car)
                && this.dateCheck(car)
            );
        });
        return res;
    }

    createSelect = (title, list, selected) => {
        const options = list.map((item, index) => <option key={index} value={item}>{item}</option>);
        return (
            <div>
            <label className='label-search' htmlFor={title}>company to rent car from</label>
            <select value={selected} name={title} id={title} onChange={this.handleChange}>
                <option value=''>Not chosen</option>
                {options}
            </select>
            </div>
        )
    }

    handleSubmit = (event) => {
        try{
            event.preventDefault();
            if (
                (this.state.fromdate.length === 0 && this.state.todate.length > 0)
                || (this.state.todate.length === 0 && this.state.fromdate.length > 0)
            ) {
                throw {message: 'A date must be selected for both date fields.'}
            }
            this.props.history.push('/Main/', {location: this.state.location, todate: this.state.todate, fromdate: this.state.fromdate})
        } catch (ex) {
            this.props.catchError(ex);
        }   
    }

    render() {
        return (
            <div className='search-field-container'>
                <label className='label-search' htmlFor='fromdate'>date to rent car from</label>
                <input type="date" name="fromdate" id="fromdate" value={this.state.fromdate} onChange={this.handleChange} />
                <label className='label-search' htmlFor='todate'>date to rent car to</label>
                <input type="date" name="todate" id="todate" value={this.state.todate} onChange={this.handleChange} />
                {this.createSelect('location', locations, this.state.location)}
                <button onClick={this.handleSubmit} >Find Bigler</button>
            </div>
        )
    }
}