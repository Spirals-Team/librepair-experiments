import React, { Component } from 'react';
import '../styles/App.css';
import facade from '../Facade';
import SideSearch from './SideSearch';
import CarList from './CarList';
import Filter from './Filter';
import Sort from './Sort';

export default class Main extends Component {
  constructor(props) {
    super(props);
    this.state = {
      category: this.props.category,
      cars: [],
      filteredCars: [],
      facade: facade,
      sortAsc: true,
      error: undefined,


    }
    this.findCars = this.findCars.bind(this);
  }

  findCars(cb) {
    this.state.facade.fetchData()
      .then((res) => {
        let cars
        (cb) ? cars = cb(res) : cars = res;
        this.setState({ cars: cars, error: undefined, filteredCars: cars })
      }).catch((ex) => this.setState({ error: ex.message + ', ' + ex.status }))
  }


  error() {
    if (this.state.error === undefined) {
      return (
        <CarList cars={this.state.filteredCars} />
      )
    } else {
      return (
        <p className="alert alert-warning">{this.state.error}</p>
      )
    }
  }

  filter = (categoryFilters, companyFilters) => {
    if (categoryFilters.length >= 1 && companyFilters.length >= 1) {
      this.filterBothCategoryAndCompany(categoryFilters, companyFilters);
    }
    else if (categoryFilters.length >= 1) {
      this.filterCategory(categoryFilters);
    }
    else {
      this.filterCompany(companyFilters);
    }

  }

  filterCompany = (companyFilters) => {
    const filteredData = this.state.cars.filter((car) => {
      for (var i = 0; i < companyFilters.length; i++) {
        if (car.company.replace(" ", "") === companyFilters[i]) {
          return true;
        }
      }
      return false;
    });
    if (filteredData.length >= 1)
      this.setState({ filteredCars: filteredData });
    else
      this.setState({ filteredCars: this.state.cars });
  }



  filterCategory = (categoryFilters) => {
    const filteredData = this.state.cars.filter((car) => {
      for (var i = 0; i < categoryFilters.length; i++) {
        if (car.category === categoryFilters[i])
          return true;
      }
      return false;
    });

    if (filteredData.length >= 1)
      this.setState({ filteredCars: filteredData });
    else
      this.setState({ filteredCars: this.state.cars });
  }

  filterBothCategoryAndCompany = (categoryFilters, companyFilters) => {
    const filteredData = this.state.cars.filter((car) => {
      var cat = false;
      var com = false;
      for (var i = 0; i < categoryFilters.length; i++) {
        if (car.category === categoryFilters[i])
          cat = true;
      }
      for (var j = 0; j < companyFilters.length; j++) {
        if (car.company.replace(" ", "") === companyFilters[j])
          com = true;
      }
      return cat && com ? true : false;
    });
    if (filteredData.length >= 1)
      this.setState({ filteredCars: filteredData });
    else
      this.setState({ filteredCars: this.state.cars });
  }

  sortingSwitch = () => {
    if (this.state.sortAsc) {
      var list = this.state.filteredCars;
      list.sort((a, b) => {
        return a.priceperday - b.priceperday;
      });
      this.setState({ sortAsc: false, filteredCars: list });
    } else {
      var list = this.state.filteredCars;
      list.sort((a, b) => {
        return b.priceperday - a.priceperday;
      });
      this.setState({ sortAsc: true, filteredCars: list });
    }
  }

  render() {
    let location = undefined;
    let todate = undefined;
    let fromdate = undefined; 
    if(this.props.location.state) {
      location = this.props.location.state.location;
      todate = this.props.location.state.todate;
      fromdate = this.props.location.state.fromdate;
    }

    return (
      <div className="grid-container-main">

        <div className="grid-item flex-container-sidenav">

          <div className="flex-item-sidenav">
            <SideSearch fetchAll={this.findCars} location={location} todate={todate} fromdate = {fromdate} />
          </div>
          
          <div className="border"/>
          
          <div className="flex-item-sidenav">
            <Filter filter={this.filter} />
          </div>

        </div>

        <div className="grid-item">
          <div className="flex-container-content">
            <Sort sortingSwitch={this.sortingSwitch} />
            {this.error()}
          </div>
        </div>
      </div>
    );
  }
}

