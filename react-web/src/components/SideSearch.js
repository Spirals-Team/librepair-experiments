import React, { Component } from 'react';
import '../styles/App.css';

const categories = ['luxury', 'mini elite', 'economy', 'standard', 'Premium'];
const locations = [
    'Cph (Copenhagen Airport)', 
    'Billund Lufthavn', 
    'Aalborg Lufthavn',,
    'Copenhagen City',
    'Aarhus City',
    'Odense',
    'Herning',
    'Roskilde',
    'Esbjerg',
    'Naestved'
];

export default class SideSearch extends Component {
    constructor(props){
        super(props)

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

                let resFromDate = new Date(reservation.fromDate);
                let resToDate = new Date(reservation.fromDate);
                let seaFromDate = new Date(this.state.fromdate);
                let seaToDate = new Date(this.state.todate);

                if(seaFromDate > resFromDate && seaFromDate < resToDate) isOK = false;
                if(seaToDate > resFromDate && seaToDate < resToDate) isOK = false;
                if(seaFromDate < resFromDate && seaToDate > resToDate) isOK = false;

                return isOK;
            })

            if(prevenEvents.length > 0) isMatch = false; 
        }
        return isMatch;
    }

    categoryCheck = (car) => {
        let isMatch = true;
        if (this.state.category.length > 0) {
            isMatch = this.state.category.toLocaleLowerCase() === car.category.toLocaleLowerCase()
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
        this.setState({[event.target.id]: event.target.value});
    }

    handleSubmit = (event) => {
        event.preventDefault();
        this.props.fetchAll(this.searchFilter);
    }

    searchFilter = (list) => {
        const res =  list.filter(car => {
            return (
                this.categoryCheck(car) 
                && this.locationCheck(car)
                && this.dateCheck(car)
            );
        });
        return res;
    }

    createSelect = (title, list, selected) => {
        const options = list.map((item, index) => <option key={index} value={item}>{item}</option>);
        return (
            <select value={selected} name={title} id={title} onChange={this.handleChange}>
                <option value=''>Not chosen</option>
                {options}
            </select>
        )
    }

    render() {
        return (
            <div>
                <input type="date" name="fromdate" id="fromdate" value={this.state.fromdate} onChange={this.handleChange}/>
                <input type="date" name="todate" id="todate" value={this.state.todate} onChange={this.handleChange}/>
                {this.createSelect('category', categories, this.state.category)}
                {this.createSelect('location', locations, this.state.location)}
                <button type="submit" onClick={this.handleSubmit}>Find Bigler</button>
            </div>
        )
    }
}