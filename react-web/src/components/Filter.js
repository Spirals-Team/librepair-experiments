import React from 'react';

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
            }
        }
    }

  handleCategoryChange = async (event) => {
    const name = event.target.name;
    const value = event.target.checked;
    let categories = Object.assign({}, this.state.categories);
    categories[name] = value;
    await this.setState({categories});

    const filterCategory = this.checkIfFilterShouldHappen(this.state.categories);
    const filterCompany = this.checkIfFilterShouldHappen(this.state.companies);

    this.props.filter(filterCategory, filterCompany);
  }

  handleCompanyChange = async (event) => {
    const name = event.target.name;
    const value = event.target.checked;
    let companies = Object.assign({}, this.state.companies);
    companies[name] = value;
    await this.setState({companies});

    const categoryFilters = this.checkIfFilterShouldHappen(this.state.categories);
    const companyFilters = this.checkIfFilterShouldHappen(this.state.companies);

    this.props.filter(categoryFilters, companyFilters);
  }

  checkIfFilterShouldHappen = (data) => {
      let filters = [];
      for(let item in data) {
          if(data[item] === true) 
            filters.push(item);
      }
      return filters;
  }

    render() {
        return (
            <div>
            <form>
                Categories
                <label style={{fontSize: 15, display: "flex", padding: 5}}>Mini 
                    <input name="Mini" type="checkbox" checked={this.state.categories.Mini} onClick={this.handleCategoryChange} />
                </label>
                <label style={{fontSize: 15, display: "flex", padding: 5}}>Economy 
                    <input name="Economy" type="checkbox" checked={this.state.categories.Economy} onClick={this.handleCategoryChange} />
                </label>
                <label style={{fontSize: 15, display: "flex", padding: 5}}>Standard
                    <input name="Standard" type="checkbox" checked={this.state.categories.Standard} onClick={this.handleCategoryChange} />
                </label >
                <label style={{fontSize: 15, display: "flex", padding: 5}}>Premium
                    <input name="Premium" type="checkbox" checked={this.state.categories.Premium} onClick={this.handleCategoryChange} />
                </label>
                <label style={{fontSize: 15, display: "flex", padding: 5}}>Luxury 
                    <input name="Luxury" type="checkbox" checked={this.state.categories.Luxury} onClick={this.handleCategoryChange} />
                </label>

               Companies
               <label style={{fontSize: 15, display: "flex", padding: 5}}>Biglers Biler
                    <input name="BiglersBigler" type="checkbox" checked={this.state.companies.BiglersBigler} onClick={this.handleCompanyChange} />
                </label>
                <label style={{fontSize: 15, display: "flex", padding: 5}}>Gert 
                    <input name="Gert" type="checkbox" checked={this.state.companies.Gert} onClick={this.handleCompanyChange} />
                </label>
                <label style={{fontSize: 15, display: "flex", padding: 5}}>Elias
                    <input name="Elias" type="checkbox" checked={this.state.companies.Elias} onClick={this.handleCompanyChange} />
                </label>
                <label style={{fontSize: 15, display: "flex", padding: 5}}>Devran
                    <input name="Devran" type="checkbox" checked={this.state.companies.Devran} onClick={this.handleCompanyChange} />
                </label>
            </form>
            </div>

        );
    }
}