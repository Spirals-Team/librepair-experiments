import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

import router from './router'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    idToken: null,
    userId: null,
    user: null
  },
  mutations: {
    authUser (state, userData) {
      state.idToken = userData.token || null
      state.userId = userData.userId
    },
    storeUser (state, user) {
      state.user = user
    },
    clearAuthData (state) {
      state.idToken = null
      state.userId = null
    }
  },
  actions: {
    setLogoutTimer ({commit}, expirationTime) {
      setTimeout(() => {
        commit('clearAuthData')
      }, expirationTime * 1000)
    },
    register ({commit, dispatch}, authData) {
      axios.post('/register', {
        ...authData
      })
        .then(res => {
          console.log(res)
          const data = res.data
          commit('authUser', {
            token: data.idToken,
            userId: data.localId
          })
          const now = new Date()
          const expirationDate = new Date(now.getTime() + data.expiresIn * 1000)
          localStorage.setItem('token', data.idToken)
          localStorage.setItem('userId', data.localId)
          localStorage.setItem('expirationDate', expirationDate)
          localStorage.setItem('email', authData.email)
          commit('storeUser', authData)
          dispatch('setLogoutTimer', data.expiresIn)
          router.replace('/')
        })
        .catch(error => console.log(error))
    },
    login ({commit, dispatch}, authData) {
      axios.post('/login', {
        ...authData
      })
        .then(res => {
          console.log(res)
          const now = new Date()
          const expirationDate = new Date(now.getTime() + res.data.expiresIn * 1000)
          localStorage.setItem('token', res.data.idToken)
          localStorage.setItem('userId', res.data.localId)
          localStorage.setItem('expirationDate', expirationDate)
          localStorage.setItem('email', authData.email)
          commit('authUser', {
            token: res.data.idToken,
            userId: res.data.localId
          })
          dispatch('storeUser', authData)
          dispatch('setLogoutTimer', res.data.expiresIn)
          router.replace('/')
        })
        .catch(error => console.log(error))
    },
    tryAutoLogin ({commit}) {
      const token = localStorage.getItem('token')
      if (!token) {
        return
      }
      const expirationDate = localStorage.getItem('expirationDate')
      const now = new Date()
      if (now >= expirationDate) {
        return
      }
      const userId = localStorage.getItem('userId')
      commit('authUser', {
        token: token,
        userId: userId
      })
    },
    logout ({commit}) {
      commit('clearAuthData')
      localStorage.removeItem('expirationDate')
      localStorage.removeItem('token')
      localStorage.removeItem('userId')
      localStorage.removeItem('email')
      router.replace('/')
    },
    storeUser ({commit, state}, userData) {
      // TODOfix user undefined after registration
      if (!state.idToken) {
        return
      }
      state.user = userData
    }
  },
  getters: {
    user (state) {
      return state.user
    },
    isAuthenticated (state) {
      return state.idToken !== null
    },
    authToken (state) {
      console.log(state.idToken)
      return state.idToken
    }
  }
})
