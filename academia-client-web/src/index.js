import React from 'react'
import { render } from 'react-dom'
import injectTapEventPlugin from 'react-tap-event-plugin'

import 'font-awesome/css/font-awesome.css'
import 'flexboxgrid/css/flexboxgrid.css'
import 'react-toastify/dist/ReactToastify.css'

import AppRouter from './components/AppRouter'
import './index.css'

injectTapEventPlugin()

render(<AppRouter />, global.document.getElementById('root'))
