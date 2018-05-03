import React, { Component } from 'react';
import '../styles/App.css';
import Main from './Main';
import Booking from './Booking';

class App extends Component {

    constructor(props){
        super(props);
    }

  render() {

//     const testCar =  {
//       "logo": "https://i.imgur.com/t0qqq7l.png",
//       "company": "Biglers Biler",
//       "category": "Mini",
//       "picture": "https://images.toyota-europe.com/dk/ag/width/1200/exterior-3.jpg",
//       "make": "Toyota",
//       "model": "Aygo",
//       "year": 2017,
//       "regno": "BAG 1234",
//       "seats": 4,
//       "doors": 4,
//       "gear": "manual",
//       "aircondition": false,
//       "location": "Hovedbane Gaarden",
//       "priceperday": 30,
//       "reservations": [
//           {
//               "companyTag": "Biglers biler",
//               "customerMail": "name@adress.dk",
//               "fromDate": "12/06/2018",
//               "toDate": "18/06/2018"
//           },
//           {
//               "companyTag": "Biglers biler",
//               "customerMail": "what@ever.dk",
//               "fromDate": "01/05/2018",
//               "toDate": "12/05/2018"
//           }
//       ]
//   }

    return (
        this.props.car ? <Booking car={this.props.car}/> : 
         <Main />  
    );
}
}

export default App;
