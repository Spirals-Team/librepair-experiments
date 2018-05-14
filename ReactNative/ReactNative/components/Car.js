import React, { Component } from 'react';
import {  View, Text, Image, TouchableOpacity} from 'react-native';
import CarInfo from './CarInfo';

export default class componentName extends Component {git
    constructor(props) {
        super(props);
        this.state = {
          showComponent: false, car: undefined
        }
        this._onButtonClick = this._onButtonClick.bind(this);
      }
    
    
      _onButtonClick(event) {
        event.preventDefault();
        if (this.state.showComponent === true) {
          this.setState({
            showComponent: false,
          });
        } else {
          this.setState({
            showComponent: true,
          });
        }
      }
    
      test(data) {
        console.log("te");
        this.setState({ car: data })
      }
    
    
      render() {
    
        return (
          <View className="Car">
            <Text className="Car-header">{this.props.car.category}</Text>
            <Image style={{width: 100, height:100}} source={{ uri: this.props.car.picture}}/>
              <TouchableOpacity onPress={this._onButtonClick} href=""><Text>Details</Text></TouchableOpacity>
            {this.state.showComponent ?
              <CarInfo car={this.props.car} /> : null
            }
            <Text className="Car-price">Price per day: {this.props.car.priceperday}</Text>
            {/*<Link to={`/Booking/${this.props.car.regno}`}>Order Car</Link>*/}
          </View>
        )
      }
}
