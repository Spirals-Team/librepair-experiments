<template>
  <div v-cloak>
  <div  class="hello" style="margin-top:10px;" >
      <b-btn v-b-toggle.collapse1 variant="primary" v-b-popover.hover="'In order to start using DailyPulseYou need to configure you fitness tracker and your google account'">Configure your tracker!</b-btn>
   <b-collapse id="collapse1" style="  margin-top:10px; ">
       <b-btn v-b-toggle.collapse1 variant="success">Fitbit</b-btn>
    <b-btn v-b-toggle.collapse1 variant="warning" v-on:click="googlefit" >Google</b-btn>
  </b-collapse>
</div>
</div>
</template>

<script>
export default {
  name: 'Connect',
  data(){
    return{
      islogin: false,
      isgoogle: true
    }
  },created: function () {
    // this.checkGoogleToken();
    // this.checkToken();
  },
   methods : {
   checkToken(){
    this.$http.get('http://localhost:8081/users/authenticateToken',{headers: {'Content-Type': 'application/json',
      'Authorization': localStorage.getItem('token')}    
         }).then((res) => {
          this.islogin = true
            }, (err) => {
          this.islogin = false
          })
    },checkGoogleToken(){
      this.$http.get('http://localhost:8081/users/verifyAccessToken',{headers: {'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('token')}    
               }).then((res) => {
                console.log(res)
                this.isgoogle = true
                  }, (err) => {
                this.isgoogle = false
                })
               console.log(this.isgoogle)
    },

    toShow(){
     return this.islogin
    },
     googlefit() {
      let url = 'https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ffitness.body.read&access_type=offline&redirect_uri=http://localhost:8080/token&response_type=code&client_id=895714867508-2t0rmc94tp81bfob19lre1lot6djoiuu.apps.googleusercontent.com'
      location.assign(url);
    }
  }
}
</script>
<style type="text/css">[v-cloak] { display:none; }</style>