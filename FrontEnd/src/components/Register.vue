<template>
      <div class="card card-container">
            <img id="profile-img" class="profile-img-card" src="../images/logo.png" />
            <form class="form-signin" @submit.prevent="register">
                <span id="reauth-email" class="reauth-email"></span>
                <input v-model="user.username" class="form-control" type="email" placeholder="Email Address" id="inputEmail" required autofocus style="    text-align: center;"/>
                <input v-model="user.name" class="form-control" type="text" placeholder="User Name" id="inputUsername" required autofocus style="    text-align: center;"/>
                <input v-model="user.password" type="password" id="inputPassword" class="form-control" placeholder="Password" required style="text-align: center;">
                <input v-model="user.password_confirmation" type="password" id="inputPasswordAgain" class="form-control" placeholder="Password Again" required style="text-align: center;">
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign Up</button>
                <!-- <button class="btn btn-lg btn-primary btn-block btn-signin" @click="register" type="submit">Sign Up</button> -->
            </form><!-- /form -->
        </div><!-- /card-container -->
</template>
<script type="application/json">
  export default{
    name: 'Register',
    data () {
      return {
        loginType: 'username',
        user: {
          username: '',
          password: '',
          name: ''
        }
      }
    },
    methods: {
      toggleLoginType () {
        this.loginType === 'username' ? this.loginType = 'email' : this.loginType = 'username'
      },
      register () {
        let url = 'http://localhost:8081/users/sign-up'
        this.$http.post(url, this.user,{credentials: true, headers: {'Content-Type': 'application/json'}}).then((res) => {
          console.log('Success', res);
          this.$router.push('/');
        }, (err) => {
          console.log('Error: ', err)
        })
      }
    }
  }
</script>
<style src="../styles/Form.css"></style>
