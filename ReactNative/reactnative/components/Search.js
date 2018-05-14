import React, { Component } from 'react';
import { View, Text, Picker, TouchableOpacity} from 'react-native';
import DatePicker from "react-native-datepicker";

const locations = [
    'Cph (Copenhagen Airport)',
    'Billund Lufthavn',
    'Aalborg Lufthavn', ,
    'Copenhagen City',
    'Aarhus City',
    'Odense',
    'Herning',
    'Roskilde',
    'Esbjerg',
    'Naestved'
];


export default class Search extends Component {
    constructor(props) {
        super(props)
        
        this.state = {
            location: "Choose location",
            fromdate: '',
            todate: '',
        }
    }

    handleChange = (event) => {
        this.setState({ [event.target.id]: event.target.value });
    }

    pickerComp = () => {
        
        return (
            <View>
                <Text>Location</Text>
                <Picker
                selectedValue = {this.state.location} onValueChange = {(locSelect)=> this.setState({location : locSelect})}
                >
 
                <Picker.Item label="Choose location" value=""/>
                <Picker.Item label="Cph (Copenhagen Airport)" value="Cph (Copenhagen Airport)"/>
                <Picker.Item label="Billund Lufthavn" value="Billund Lufthavn"/>
                <Picker.Item label='Aalborg Lufthavn' value='Aalborg Lufthavn'/>
                <Picker.Item label='Copenhagen City' value='Copenhagen City'/>
                <Picker.Item label='Aarhus City' value='Aarhus City'/>
                <Picker.Item label='Odense' value='Odense'/>
                <Picker.Item label='Herning' value='Herning'/>
                <Picker.Item label='Roskilde' value='Roskilde'/>
                <Picker.Item label='Esbjerg' value='Esbjerg'/>
                <Picker.Item label='Naestved' value='Naestved'/>
    
                </Picker>
               
            </View>
        )
    }

    render() {
        const { navigate } = this.props.navigation;

        return (
            <View>
                <Text>Rental from date</Text>
                <DatePicker 
                mode="date" 
                format="YYYY-MM-DD" 
                confirmBtnText="Confirm"
                cancelBtnText="Cancel"
                placeholder="rent from"
                onDateChange={(date) => {this.setState({fromdate: date})}}
                /> 
                <Text>Rental to date</Text>
                <DatePicker 
                mode="date" 
                format="YYYY-MM-DD" 
                confirmBtnText="Confirm"
                cancelBtnText="Cancel"
                placeholder="rent to"
                onDateChange={(date) => {this.setState({todate: date})}}
                /> 
                
                {this.pickerComp()}
                
                <Text>{JSON.stringify(this.state)}</Text>

                {/* TODO : add navigator functionality */}
                <TouchableOpacity 
                // test style
                style={{backgroundColor: '#DDDDDD', padding: 10, alignItems : "center"}}
                onPress={() => navigate('Mainpage')}
                >
                    <Text>
                       Search 
                    </Text>
                </TouchableOpacity>

            </View>
        )
    }
}
