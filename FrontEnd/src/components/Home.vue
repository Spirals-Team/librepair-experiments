<template>
  <div class="hello">
    <!-- <h1 style="  margin-top:50px;">Good Evening,<b>{{ msg }}</b> (: </br></h1> -->
   <div style=" width:70%; margin:auto; margin-top: 20px;">
     <b-jumbotron style="background-color: rgba(44,144,205,0.4)" text-variant="white" border-variant="dark">
  <template slot="header">
    Welcome to DailyPulse!
  </template>
  <template slot="lead">
    This webapp will help you track your heart rate within events.
  </template>
  <hr class="my-4">
  <p>
    It uses Google fit and Google calander in order to get your data.
  </p>
</b-jumbotron>
   </div>
  </br>
  </br>
<!--     <Chart id="chart" style="width:50%;   margin:  0 auto;
  background-color: rgba(33,39,51,0.25);
  border-radius: 25px;
  width:50%;
  height:20%;
    "></Chart> -->
      </br>
  </div>
</template>


<script>
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VueResource from 'vue-resource'
import BootstrapVue from 'bootstrap-vue'
export default {
  name: 'HelloWorld',
  components: {  },
  data () {
    return {
      msg: '',
      token: localStorage.getItem('token')
    }
  },
   created: function () {
          this.getMessages();
        },
  methods : {
     getMessages () {
            this.$http.get('http://localhost:8081/users/username',{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token')}
            }).then((res) => {
              this.msg = res.bodyText;
            }
            , (err) => {
              console.log(err);
          })
        },
  logout () {
    localStorage.setItem('loggedIn', 'false');
    localStorage.setItem('username', '');
    localStorage.setItem('token', 'false');
    location.reload();
    // let url = "http://localhost:8081/logout";
    // this.$http.get(url).then((res) => {
    //   localStorage.setItem('loggedIn', 'false');
    //   localStorage.setItem('username', '');
    // 	localStorage.setItem('token', 'false');
    //   location.reload();
    // 	this.$store.dispatch(types.LOGOUT);
    // 	token: ''
    // 	username: ''
    // });
  }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
<style src="../styles/Form.css"></style>
<!-- <script src="//unpkg.com/babel-polyfill@latest/dist/polyfill.min.js"></script>
<script src="//unpkg.com/bootstrap-vue@latest/dist/bootstrap-vue.js"></script> -->