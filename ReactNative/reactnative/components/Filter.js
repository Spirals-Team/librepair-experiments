import React from 'react';
import { View, Text, TouchableOpacity } from 'react-native';
import CheckboxGroup from 'react-native-checkbox-group';
import styles from '../Styles';

export default class Filter extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            categories: {
                Mini: false,
                Economy: false,
                Standard: false,
                Premium: false,
                Luxury: false
            },
            companies: {
                BiglersBigler: false,
                Gert: false,
                Elias: false,
                Devran: false
            },
            display: false
        }
    }

    handleCategoryChange = async (selected) => {
        let categories = Object.assign({}, this.state.categories);
        for (var prop in categories) {
            selected.includes(prop) ? categories[prop] = true : categories[prop] = false;
        }
        await this.setState({categories});
        console.log(this.state);
        const filterCompany = this.checkIfFilterShouldHappen(this.state.companies);
        this.props.filter(selected, filterCompany);
    }

    handleCompanyChange = async (selected) => {
        let companies = Object.assign({}, this.state.companies);
        for (var prop in companies) {
            selected.includes(prop) ? companies[prop] = true : companies[prop] = false;
        }
        await this.setState({companies});
        console.log(this.state);
        const filterCategory = this.checkIfFilterShouldHappen(this.state.categories);
        this.props.filter(filterCategory, selected);
    }

    checkIfFilterShouldHappen = (data) => {
        let filters = [];
        for (let item in data) {
            if (data[item] === true)
                filters.push(item);
        }
        return filters;
    }

    displayFilterSection = () => {
        this.setState({display: !this.state.display});
    }

    render() {
        return (
            <View>
                {
                this.state.display ? 
                    <View style={styles.filter}>
                            <TouchableOpacity
                                style={styles.filterOn}
                                onPress={this.displayFilterSection}
                            >
                                <Text> Filter </Text>
                            </TouchableOpacity>
                        <View style={{}}>
                        <Text>Categories</Text>
                        <CheckboxGroup
                            callback={this.handleCategoryChange}
                            iconColor={"#00a2dd"}
                            iconSize={30}
                            checkedIcon="ios-checkbox-outline"
                            uncheckedIcon="ios-square-outline"
                            checkboxes={[
                                {
                                    label: "Mini", // label for checkbox item
                                    value: "Mini", // selected value for item, if selected, what value should be sent?
                                    // if the item is selected by default or not.
                                    selected: this.state.categories.Mini
                                },
                                {
                                    label: "Economy",
                                    value: "Economy",
                                    selected: this.state.categories.Economy
                                },
                                {
                                    label: "Standard",
                                    value: "Standard",
                                    selected: this.state.categories.Standard
                                },
                                {
                                    label: "Premium",
                                    value: "Premium",
                                    selected: this.state.categories.Premium
                                },
                                {
                                    label: "Luxury",
                                    value: "Luxury",
                                    selected: this.state.categories.Luxury
                                },
                            ]}
                            labelStyle={{
                                color: '#333',
                                margin: 5
                            }}
                            rowStyle={{
                                flexDirection: 'row'
                            }}
                            rowDirection={"column"}
                        />
                        </View>
                        <View style={{}}>
                        <Text>Companies</Text>
                        <CheckboxGroup
                            callback={this.handleCompanyChange}
                            iconColor={"#00a2dd"}
                            iconSize={30}
                            checkedIcon="ios-checkbox-outline"
                            uncheckedIcon="ios-square-outline"
                            checkboxes={[
                                {
                                    label: "BiglersBigler", // label for checkbox item
                                    value: "BiglersBigler", // selected value for item, if selected, what value should be sent?
                                    // if the item is selected by default or not.
                                    selected: this.state.companies.BiglersBigler
                                },
                                {
                                    label: "Gert",
                                    value: "Gert",
                                    selected: this.state.companies.Gert
                                },
                                {
                                    label: "Elias",
                                    value: "Elias",
                                    selected: this.state.companies.Elias
                                },
                                {
                                    label: "Devran",
                                    value: "Devran",
                                    selected: this.state.companies.Devran
                                }
                            ]}
                            labelStyle={{
                                color: '#333', 
                                margin: 5
                            }}
                            rowStyle={{
                                flexDirection: 'row'
                            }}
                            rowDirection={"column"}
                        />
                        </View>
                    </View>
                :
                        <TouchableOpacity
                            style={styles.filterOff}
                            onPress={this.displayFilterSection}
                        >
                            <Text> Filter </Text>
                        </TouchableOpacity>
                }
            </View>
        );
    }
}