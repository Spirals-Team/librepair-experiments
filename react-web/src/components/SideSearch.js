import React, { Component } from 'react';
import '../styles/App.css';

const categories = ['luxury', 'mini elite', 'economy', 'standard'];
const locations = ['kastrup lufthavn', 'hovedbane gaarden', 'roskilde'];

export default class SideSearch extends Component {
    constructor(props){
        super(props)

        const now = new Date();
        const day = ('0' + now.getDate());
        const month = ('0' + now.getMonth());
        const year =  now.getFullYear();

        this.state = {
            category: categories[0],
            location: locations[0],
            date: year + '-' + month + '-' + day
        }
    }

    handleChange = (event) => {
        this.setState({[event.target.id]: event.target.value});
    }

    createSelect = (title, list, selected) => {
        const options = list.map((item, index) => <option key={index} value={item}>{item}</option>);
        return (
            <select value={selected} name={title} id={title} onChange={this.handleChange}>
                {options}
            </select>
        )
    }

    render() {
        return (
            <div>
                <input type="date" name="date" id="date" value={this.state.date} onChange={this.handleChange}/>
                {this.createSelect('category', categories, this.state.category)}
                {this.createSelect('location', locations, this.state.location)}
                <button type="submit">Find Bigler</button>
            </div>
        )
    }
}