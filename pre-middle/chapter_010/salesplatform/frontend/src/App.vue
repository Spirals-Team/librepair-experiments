<template>
  <div id="app">
    <job4j-header/>
    <router-view></router-view>
  </div>
</template>

<script>
import Job4jHeader from './components/Header.vue'

export default {
  name: 'app',
  components: {
    'job4j-header': Job4jHeader
  },
  created () {
    this.$store.dispatch('tryAutoLogin')
  }
}
</script>

<style scoped>
  body, html {
    margin: 0;
    font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  }
</style>
