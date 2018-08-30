// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
import axios from 'axios'

import router from './router'
import store from './store'

import App from './App.vue'

Vue.use(BootstrapVue)
axios.defaults.headers.common['Access-Control-Allow-Origin'] = 'localhost'

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
