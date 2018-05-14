import React, { Component } from 'react';
import { Link } from 'react-router-dom'
import StartSearch from './StartSearch';

export default class Startpage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            error: undefined
        }
    }

    catchError = (error) => {
        this.setState({error: error})
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
            <div className='grid-container-start'>
                <div className='start-search-container'>
                    <img alt="" src='https://i.imgur.com/t0qqq7l.png'></img>
                    <StartSearch history={this.props.history} catchError={this.catchError}/>
                    {this.error()}
                </div>
            </div>
        )
    }
}



