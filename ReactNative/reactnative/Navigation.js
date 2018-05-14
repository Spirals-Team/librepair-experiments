import React from 'react';
import { SwitchNavigator } from 'react-navigation';

import Mainpage from './components/Mainpage';
import Search from './components/Search';

const Nav = SwitchNavigator({
      Search: {screen : Search },
      Mainpage: {screen : Mainpage}
    })


export default Nav;