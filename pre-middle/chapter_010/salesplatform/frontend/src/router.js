import Vue from 'vue'
import VueRouter from 'vue-router'

import store from './store'

import PostListPage from './components/PostList.vue'
import CreatePostPage from './components/CreatePost.vue'
import RegisterPage from './components/auth/Register.vue'
import LoginPage from './components/auth/Login.vue'

Vue.use(VueRouter)

const routes = [
  {path: '/', component: PostListPage},
  {path: '/register', component: RegisterPage},
  {path: '/login', component: LoginPage},
  {
    path: '/create',
    component: CreatePostPage,
    beforeEnter (to, from, next) {
      if (store.state.idToken) {
        next()
      } else {
        next('/login')
      }
    }
  }
]

export default new VueRouter({mode: 'history', routes})
