import React, { Component } from 'react';
import { View, Text, TouchableOpacity } from 'react-native';

export default class Sort extends Component {

  render() {
    return (
      <View>
        <TouchableOpacity onPress={this.props.sortingSwitch}>
          <Text>
            Sort by Price
        </Text>
        </TouchableOpacity>
      </View>
    )
  }
};