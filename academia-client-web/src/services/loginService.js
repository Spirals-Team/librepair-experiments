import apiService from './apiService'

import accountTypes from '../constants/accountTypes'

let isAuthenticated = false
let user = null

class LoginService {
  isAuthenticated() {
    return isAuthenticated
  }

  getUser() {
    return user
  }

  isAdmin() {
    return user.authorities[0].authority === accountTypes.ADMIN
  }

  isCustomer() {
    return user.authorities[0].authority === accountTypes.CUSTOMER
  }

  isTrainer() {
    return user.authorities[0].authority === accountTypes.TRAINER
  }

  checkAuthentication() {
    return apiService.get('/api/private/check')
      .then(({ data }) => {
        user = data
        isAuthenticated = true
      })
  }

  /*
    auth:
    {
      username: '',
      password: '',
    }
  */
  login(auth) {
    let options = {}

    if (auth) {
      options = {
        ...options,
        auth,
      }
    }

    return apiService.get('/api/public/login', options)
      .then(({ data }) => {
        user = data
        isAuthenticated = true
      })
  }

  logout() {
    return apiService.get('/api/private/logout')
      .then(() => {
        user = null
        isAuthenticated = false
      })
  }
}

export default new LoginService()
