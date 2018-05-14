import React, { Component } from 'react';
import '../styles/App.css';

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
            location: '',
            fromdate: '',
            todate: '',
            error: undefined
        }
    }

  async componentDidMount(){
      console.log(this.props)
        let location = ''
        let fromdate = ''
        let todate = ''

        if(this.props.location) location = this.props.location
        if(this.props.fromdate) fromdate = this.props.fromdate
        if(this.props.todate) todate = this.props.todate

       await this.setState({location: location, fromdate: fromdate, todate: todate});
        console.log("DEBUG");
        this.props.fetchAll(this.searchFilter);

    }

    dateCheck = (car) => {
        let isMatch = true;
        if (this.state.fromdate.length > 0 && this.state.todate.length > 0) {
            const prevenEvents = car.reservations.filter(reservation => {
                let isNotOK = true;

                let resFromDate = new Date(reservation.fromdate);
                let resToDate = new Date(reservation.todate);
                let seaFromDate = new Date(this.state.fromdate);
                let seaToDate = new Date(this.state.todate);

                if(seaFromDate > resFromDate && seaFromDate < resToDate) isNotOK = true;
                if(seaToDate > resFromDate && seaToDate < resToDate) isNotOK = true;
                if(seaFromDate < resFromDate && seaToDate > resToDate) isNotOK = true;

                return isNotOK;
            })

            if(prevenEvents.length > 0) isMatch = false; 
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
        try{
            event.preventDefault();
            if (
                (this.state.fromdate.length === 0 && this.state.todate.length > 0)
                || (this.state.todate.length === 0 && this.state.fromdate.length > 0)
            ) {
                throw {message: 'A date must be selected for both date fields.'}
            }
            this.props.fetchAll(this.searchFilter);
            this.setState({error: undefined})
        } catch (ex) {
            this.setState({error: ex})
        }   
    }

    searchFilter = (list) => {
        const res =  list.filter(car => {
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
            <label className='label-search' htmlFor={title}>company to rent the car from</label>
                <select value={selected} name={title} id={title} onChange={this.handleChange}>
                    <option value=''>Not chosen</option>
                    {options}
                </select>
            </div>
        )
    }

    error = () => {
        if(this.state.error) {
            return (
                <div className='error-container'>
                    {this.state.error.message}
                </div>
            )
        }
        
    }

    render() {
        return (
            <div>
                <label className='label-search' htmlFor='fromdate'>date to rent car from</label>
                <input type="date" name="fromdate" id="fromdate" value={this.state.fromdate} onChange={this.handleChange}/>
                <br/>
                <label className='label-search' htmlFor='todate'>date to rent car to</label>
                <input type="date" name="todate" id="todate" value={this.state.todate} onChange={this.handleChange}/>
                <br/>
                {this.createSelect('location', locations, this.state.location)}
                <br/>
                {this.error()}
                <button type="submit" onClick={this.handleSubmit}>Find Bigler</button>
            </div>
        )
    }
}