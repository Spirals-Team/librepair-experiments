<template>
  <header>
    <div>
      <nav class="header-nav">
        <div class="header-logo">
          <router-link to="/">
            <h1>Sale's Platform</h1>
          </router-link>
        </div>
        <ul class="toolbar">
          <li class="header-nav-postad ">
            <router-link to="/create" class="btn-header">
              <span class="btn-header-txt">Post an ad</span>
            </router-link>
          </li>
          <li v-if="auth" class="header-nav-postad ">
            <span class="btn-header-txt">{{userEmail.email}}</span>
          </li>
          <li v-if="!auth" class="header-nav-login">
            <router-link to="/login" class="header-nav-link">Login</router-link>
          </li>
          <li v-if="!auth" class="header-nav-login">
            <router-link to="/register" class="header-nav-link">Register
            </router-link>
          </li>
          <li v-if="auth" class="header-nav-login">
            <span @click="onLogout"
                  class="btn-logout btn-header-txt header-nav-link">Logout
            </span>
          </li>
        </ul>
      </nav>
    </div>
  </header>
</template>

<script>
export default {
  computed: {
    auth () {
      return this.$store.getters.isAuthenticated
    },
    userEmail () {
      return this.$store.getters.user
    }
  },
  methods: {
    onLogout () {
      this.$store.dispatch('logout')
    }
  }
}
</script>

<style scoped>
  header {
    background-color: #d8d6d9;
    color: #f0ece6;
    min-height: 100%;
    position: relative;
    z-index: 90;
    transition: margin-bottom 333ms ease-in-out;
    display: block;
    flex-flow: row;
  }

  header > div {
    padding: 0;
    display: block;
    margin-right: auto;
    margin-left: auto;
    position: relative;
    background: #3c3241;
  }

  .header-logo {
    float: left;
    line-height: 0;
    font-weight: bold;
    color: white;
  }

  .header-logo a {
    display: inline-block;
    padding-top: 8px;
    padding-left: 8px;
    text-decoration: none;
    color: #3997ba;
    cursor: pointer;
  }

  .header-nav {
    padding: 10px 7px;
    overflow: hidden;
    display: block;
  }

  ul.toolbar {
    margin-top: 16px;
    margin-bottom: 16px;
    display: flex;
    flex-direction: row-reverse;
    list-style: none;
  }

  ul > li {
    list-style: none;
  }

  .header-nav-postad {
    padding-left: 30px;
  }

  .header-nav-link {
    background-image: none;
    border: 0;
    box-shadow: none;
    width: auto;
    height: auto;
    padding-top: 0;
    line-height: 35px;
    display: inline-block;
    text-align: center;
    color: #f0ece6;
    text-decoration: none;
  }

  .header-nav-link:hover {
    color: #b1adb3;
    text-decoration: none;
  }

  .header-nav-login {
    float: right;
    margin: 2px 0 0;
    padding-left: 30px;
  }

  .btn-header {
    border: 1px solid #e57373;
    background: inherit;
    color: #fff;
    padding: 0 12px;
    transition-duration: 222ms;
    position: relative;
    display: inline-block;
    border-radius: 2px;
    vertical-align: middle;
    height: 35px;
    line-height: 31px;
    overflow: hidden;
    text-align: center;
    font-family: inherit;
    font-size: 16px;
    font-weight: 400;
    text-decoration: none;
  }

  .btn-header:hover {
    color: #fff;
    background-color: #e57373;
  }

  .btn-header-txt {
    color: #fff;
    line-height: 31px;
    text-align: center;
    font-family: inherit;
    font-size: 16px;
    font-weight: 400;
  }

  .btn-logout {
    cursor: pointer;
  }
</style>
