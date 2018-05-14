import React, { Component } from 'react';
import {View, Text, ScrollView} from 'react-native';
import facade from '../Facade';
//import SideSearch from './SideSearch';
import CarList from './CarList';
import Filter from './Filter';
import Sort from './Sort';

export default class Main extends Component {
  constructor(props) {
    super(props);
    this.state = {
      cars: [],
      filteredCars: [],
      facade: facade,
      sortAsc: true,
      error: undefined
    }
    this.findCars = this.findCars.bind(this);
  }

  componentDidMount() {
    this.findCars();
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
        <View>
            <CarList cars={this.state.filteredCars} />
        </View>
      )
    } else {
      return (
        <Text className="alert alert-warning">{this.state.error}</Text>
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
    return (
      <ScrollView className="grid-container-main">
        <View className="grid-item flex-container-sidenav">
          <View className="flex-item-sidenav-search">
            {/*<SideSearch fetchAll={this.findCars} />*/}
          </View>
          <View className="flex-item-sidenav-filter">
            <Filter filter={this.filter} /> 
          </View>
        </View>
        <View className="grid-item">
          <View>
          </View>
            <Sort sortingSwitch={this.sortingSwitch} />
          <View className="flex-container-content">
            {this.error()}
          </View>
        </View>
      </ScrollView>
    );
  }
}
