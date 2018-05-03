import React, { Component } from 'react';
import '../styles/App.css';
import facade from '../Facade';
import SideSearch from './SideSearch';
import CarList from './CarList';

export default class Main extends Component {
  constructor(props) {
    super(props);
    this.state = {
      cars: [],
      error: undefined
    }
  }

  componentDidMount() {
    facade.fetchData()
      .then((res) => {
        this.setState({ cars: res, error: undefined })
      }).catch((ex) => this.setState({ error: ex.message + ', ' + ex.status }))
  }

  error() {
    if (this.state.error === undefined) {
      return (
        <CarList cars={this.state.cars} />
      )
    } else {
      return (
        <p className="alert alert-warning">{this.state.error}</p>
      )
    }
  }

  render() {
    return (     
        <div className="grid-container-main">
          <div className="grid-item flex-container-sidenav">
            <div className="flex-item-sidenav-search">
              <SideSearch />
            </div>
            <div className="flex-item-sidenav-filter">
              Filter
          </div>
          </div>
          <div className="grid-item">
            <div className="flex-container-content">


              {this.error()}


            <CarList cars={this.state.cars}/>
            </div>
          </div>
        </div>
    );
  }
}
