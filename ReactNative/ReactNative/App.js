import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import Mainpage from './components/Mainpage';
import Search from './components/Search';
import Nav from "./Navigation";

export default class App extends React.Component {
  
  
  render() {

    return (
      <View style={styles.container}>
        {/* <Mainpage /> */}
        {/* <Search/> */}
      <Nav />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
