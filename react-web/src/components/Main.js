import React, { Component } from 'react';
import '../styles/App.css';

import facade from '../Facade';

import SideSearch from './SideSearch';
import CarList from './CarList';
import Filter from './Filter';



export default class Main extends Component {
    constructor(props){
        super(props);
        this.state = {
            cars: [],
            filteredCars: []
        }
    }

    componentDidMount(){
        try{
        facade.fetchData()
        .then((res) => {
            this.setState({cars: res,
                          filteredCars: res})
        })
        } catch (ex) {
            console.log(ex)
        }
    }

    filter = (categoryFilters, companyFilters) => {
      if(categoryFilters.length >= 1 && companyFilters.length >= 1) {
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
        for(var i = 0; i < companyFilters.length; i++) {
          if(car.company.replace(" ", "") === companyFilters[i]) {
            return true;
          }
      }
        return false;
      }); 

      if(filteredData.length >= 1)
        this.setState({filteredCars: filteredData});
      else 
        this.setState({filteredCars: this.state.cars});
    }

    filterCategory = (categoryFilters) => {
      const filteredData = this.state.cars.filter((car) => {
        for(var i = 0; i < categoryFilters.length; i++) {
            if(car.category === categoryFilters[i])
              return true;
        }
        return false;
      }); 

      if(filteredData.length >= 1)
        this.setState({filteredCars: filteredData});
      else 
        this.setState({filteredCars: this.state.cars});
    }

    filterBothCategoryAndCompany = (categoryFilters, companyFilters) => {
      const filteredData = this.state.cars.filter((car) => {
        var cat = false;
        var com = false;
        for(var i = 0; i < categoryFilters.length; i++) {
          if(car.category === categoryFilters[i])
            cat = true;
        }
      for(var j = 0; j < companyFilters.length; j++) {
        if(car.company.replace(" ", "") === companyFilters[j])
          com = true;
      }
        return cat && com ? true : false;
      });
      if(filteredData.length >= 1)
        this.setState({filteredCars: filteredData});
      else 
        this.setState({filteredCars: this.state.cars});
    }

  render() {
    return (
      <div className="grid-container-main">
        <div className="grid-item flex-container-sidenav">
          <div className="flex-item-sidenav-search">
            <SideSearch />
          </div>
          <div className="flex-item-sidenav-filter">
            <Filter filter={this.filter}/>
          </div>
        </div>
        <div className="grid-item">
          <div className="flex-container-content">
            <CarList cars={this.state.filteredCars}/>
          </div>
        </div>
      </div>
    );
  }
}

// const cars = [

//   {
//   // "logo": "https://imgur.com/t0qqq7l",
//   "logo":"https://i.imgur.com/t0qqq7l.png",
//   "company": "Biglers Bigler",
//   "category": "Mini",
//   "picture": "https://icdn5.digitaltrends.com/image/2015-mini-cooper-s-hardtop-0018-800x533-c.jpg",
//   "make": "Mini Cooper",
//   "model": "Mini Cooper S",
//   "year": 2017,
//   "regno": "BUF 9330",
//   "seats": 5,
//   "doors": 5,
//   "gear": "Manual",
//   "aircondition": false,
//   "location": "Copenhagen City",
//   "priceperday": 900,
//   "reservations": [
//       {
//           "companyTag": "Biglers biler",
//           "customerMail": "y@Cooper.dk",
//           "fromDate": "01/01/2018",
//           "toDate": "14/01/2018"
//       },
//       {
//           "companyTag": "Biglers biler",
//           "customerMail": "y@ss.dk",
//           "fromDate": "15/02/2018",
//           "toDate": "15/03/2018"
//       }
//   ]
//   },

//   {
//       // "logo": "https://imgur.com/t0qqq7l",
//       "logo":"https://i.imgur.com/t0qqq7l.png",
//       "company": "Biglers Bigler",
//       "category": "Mini",
//       "picture": "https://icdn5.digitaltrends.com/image/2015-mini-cooper-s-hardtop-0018-800x533-c.jpg",
//       "make": "Mini Cooper",
//       "model": "Mini Cooper S",
//       "year": 2017,
//       "regno": "BUF 9330",
//       "seats": 5,
//       "doors": 5,
//       "gear": "Manual",
//       "aircondition": false,
//       "location": "Copenhagen City",
//       "priceperday": 900,
//       "reservations": [
//           {
//               "companyTag": "Biglers biler",
//               "customerMail": "y@Cooper.dk",
//               "fromDate": "01/01/2018",
//               "toDate": "14/01/2018"
//           },
//           {
//               "companyTag": "Biglers biler",
//               "customerMail": "y@ss.dk",
//               "fromDate": "15/02/2018",
//               "toDate": "15/03/2018"
//           }
//       ]
//       },

//       {
//           // "logo": "https://imgur.com/t0qqq7l",
//           "logo":"https://i.imgur.com/t0qqq7l.png",
//           "company": "Biglers Bigler",
//           "category": "Mini",
//           "picture": "https://icdn5.digitaltrends.com/image/2015-mini-cooper-s-hardtop-0018-800x533-c.jpg",
//           "make": "Mini Cooper",
//           "model": "Mini Cooper S",
//           "year": 2017,
//           "regno": "BUF 9330",
//           "seats": 5,
//           "doors": 5,
//           "gear": "Manual",
//           "aircondition": false,
//           "location": "Copenhagen City",
//           "priceperday": 900,
//           "reservations": [
//               {
//                   "companyTag": "Biglers biler",
//                   "customerMail": "y@Cooper.dk",
//                   "fromDate": "01/01/2018",
//                   "toDate": "14/01/2018"
//               },
//               {
//                   "companyTag": "Biglers biler",
//                   "customerMail": "y@ss.dk",
//                   "fromDate": "15/02/2018",
//                   "toDate": "15/03/2018"
//               }
//           ]
//           }
// ];