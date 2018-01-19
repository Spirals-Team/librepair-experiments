<template>
  <div>
    <div class="hello">
      <p></p>

  <h2 style="font-family: 'Muli', sans-serif;">Configure Page <b-badge>New</b-badge></h2>    </div>
  <p></p>
  <Connect></Connect>
<div class="Change Password" style="margin-top:10px;">
   <div style ="width:25%; margin:auto">
     <b-card  bg-variant="dark" text-variant="white" title="Enter Your Desired Password And Confirm It">
       <form class="form-signin" @submit.prevent="changePass">
       <p v-if="toggleMsg" style="color:red"> {{msg}}</p>
       <input v-model="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required style="text-align: center;">
       <p></p>
       <input v-model="rePassword" type="password" id="inputPasswordAgain" class="form-control" placeholder="Password Again" required style="text-align: center;">
       <p></p>
       <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Change Password</button>
     </form>
     </b-card>
     
   </div>
</div>  
</div>
</template>
<script>
import Connect from './Connect'
export default {
  name: 'Config',
  components:{ Connect },
  data() {
    return {
      password : '',
      rePassword : '',
      toggleMsg : false,
      msg : ''
    }
  },
  methods: {
    created: function () {
      this.msg = ''
      this.toggleMsg = false
    },
    changePass() {
      this.toggleMsg = true
      if (this.password.localeCompare(this.rePassword) != 0){
        this.msg = 'Passwords Must Match'
      } else if (this.password.length < 6){
        this.msg = 'Password Length Must Be At Least 6'
      } else {
        let url = 'http://localhost:8081/users/changepassword'
        this.$http.post(url,password, {headers: {'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('token')}}).then((res) => {
          this.msg = 'Password Changed Succefully'
        }, (err) => {
          console.log('Error')
        })
      }
    },
    googlefit() {
      let url = 'https://accounts.google.com/o/oauth2/v2/auth?scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Ffitness.activity.read+https://www.googleapis.com/auth/calendar.readonly&access_type=offline&redirect_uri=http://localhost:8080/token&response_type=code&client_id=128078459465-4bjs62f5pg8bmodena4ojqr5f78i709i.apps.googleusercontent.com'
      location.assign(url);
    }
  }
}

</script>
