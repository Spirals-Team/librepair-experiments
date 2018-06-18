import React, { Component } from 'react'

import Loader from 'react-loader'

import DashboardAdmin from './DashboardAdmin'
import DashboardCustomer from './DashboardCustomer'
import DashboardTrainer from './DashboardTrainer'

import dashboardService from '../../../../services/dashboardService'
import loginService from '../../../../services/loginService'

export default class Dashboard extends Component {
  state = {
    dashboard: null,

    didLoad: false,
  }

  componentDidMount() {
    dashboardService.all()
      .then(data => this.setState({ dashboard: data, didLoad: true }))
  }

  renderDashboardForAccountType(dashboard) {
    if (loginService.isAdmin()) {
      return <DashboardAdmin data={dashboard} />
    }
    if (loginService.isCustomer()) {
      return <DashboardCustomer data={dashboard} />
    }
    if (loginService.isTrainer()) {
      return <DashboardTrainer data={dashboard} />
    }
    return null
  }

  render() {
    const { didLoad } = this.state

    return (
      <div>
        <Loader loaded={didLoad}>
          {didLoad ? this.renderDashboardForAccountType(this.state.dashboard) : null}
        </Loader>
      </div>
    )
  }
}
