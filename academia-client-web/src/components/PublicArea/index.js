import React from 'react'
import PropTypes from 'prop-types'
import { Route, Redirect } from 'react-router-dom'

import Login from './Login'

import { LOGIN } from '../../config/routes'

const RedirectToLogin = () => (
  <Redirect
    to={LOGIN}
  />
)

const PublicArea = props => (
  <div>
    <Route exact path="/" component={RedirectToLogin} />
    <Route path={`${props.match.url}`} component={Login} />
  </div>
)

PublicArea.propTypes = {
  match: PropTypes.shape({
    url: PropTypes.string,
  }).isRequired,
}

export default PublicArea
