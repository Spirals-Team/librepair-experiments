import React, { Component } from 'react';
import { View, Text, Image} from 'react-native';

export default class componentName extends Component {
    render() {
        return (
            <View className="car-data">
                <Image style={{height:100, width:100}} source={{ uri :this.props.car.logo}} />
                <Text>company: {this.props.car.company}</Text>
                <Text>make: {this.props.car.make}</Text>
                <Text>model: {this.props.car.model}</Text>
                <Text>year: {this.props.car.year}</Text>
                <Text>regno: {this.props.car.regno}</Text>
                <Text>seats: {this.props.car.seats}</Text>
                <Text>doors: {this.props.car.doors}</Text>
                <Text>gear: {this.props.car.gear}</Text>
                <Text>aircondition: {this.props.car.aircondition.toString()}</Text>
                <Text>location: {this.props.car.location}</Text>
            </View>
        );
    }
}
