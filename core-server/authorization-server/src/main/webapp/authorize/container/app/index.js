// @flow
'use strict';

import React, {Component} from 'react';

import FlashMessageList from '../common/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

class App extends Component<App.propTypes> {
  render() {
    return (
      <div className='container'>
        <FlashMessageList/>
        {this.props.children}
      </div>
    );
  }
}

export default App;