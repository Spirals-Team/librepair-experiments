import React, { Component } from 'react';
import '../styles/App.css';
import Main from './Main';
import Booking from './Booking';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import Startpage from './Startpage';

class App extends Component {

    constructor(props) {
        super(props);
    }

    render() {

        return (
            <div className="grid-container-frame">
                <div className="frame-grid-item">
                
                </div>
                <div className="frame-grid-item">
                <Router basename={`${this.props.history.location.pathname}#`}>
                    <Switch>
                        <Route exact path="/" component={Startpage} />
                        <Route exact path="/Main" component={Main} />
                        <Route path="/Booking/:car" component={Booking}/>   
                    </Switch>
                </Router>
                </div >
            </div >

        );
    }
}

export default App;
