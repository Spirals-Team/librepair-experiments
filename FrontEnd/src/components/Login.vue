<template>
<div class="container">
        <div class="card card-container">
            <img id="profile-img" class="profile-img-card" src="../images/logo.png"/>
            <form class="form-signin" @submit.prevent="login">
            	      <p v-if="authFailed">Invalid Username and Password</p>
                <span id="reauth-email" class="reauth-email"></span>
                <input class="form-control" v-model="user.username" type="email" placeholder="Email Address" id="inputEmail" required autofocus style="    text-align: center;"/>
                <input type="password" id="inputPassword" v-model="user.password" class="form-control" placeholder="Password" required style="text-align: center;">
                <div id="remember" class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> Remember me
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>

            </form><!-- /form -->
              <router-link to="/register">Create an account
            </router-link>
            <a href="#" class="forgot-password">
                Forgot the password?
            </a>
            
        </div><!-- /card-container -->
    </div>
</template>
<script type="text/javascript">

  export default {
    name: 'Login',
    data() {
      return {
        logged: false,
        authFailed : false,
        user : {
          username : '',
          password : ''
        }
      }
    } ,
    methods : {
      login () {
        let url = "http://localhost:8081/login";
				let params = {"username": this.user.username,"password": this.user.password};
				params = JSON.stringify(params);
          // send post request
          this.$http.post(url, params, {credentials: true, headers: {'Content-Type': 'application/json'}}).then((res) => {
          // success callback
          location.reload();
					localStorage.setItem('token', res.headers.get('authorization'));
        }, (err) => {
          console.log(err);
          // error callback
        });
        },

        logout () {
					localStorage.setItem('token', 'false');
					location.reload();
        }
      }
    }

  </script>
<style src="../styles/Form.css"></style>
