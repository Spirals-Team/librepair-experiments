<template>
</template>
<script>
export default {
  name: 'GoogleFit',
  data(){
    return{
      accessToken: '',
      refreshToken: '',
      accessCode: '',
      succesful: false
    }
  },created: function () {
    // console.log(this.succesful)
    if(this.succesful){
      console.log('second time ?')
      // this.$http.post('http://localhost:8081/users/updataGoogleFitToken',{
      //         "first": this.accessToken.toString() ,
      //         "second": this.refreshToken.toString()
      //       }
      //        ,{headers: {'Content-Type': 'application/json',
      //         'Authorization': localStorage.getItem('token'),}
      //       }).then((res) =>{
      //         console.log(res);
      //         console.log(successsssss);
      //       },(err) =>{
      //         console.log(err);
      //         console.log(faillllll);
      //       })
      //       console.log('hi')
      //           window.location.href = "http://localhost:8080/config/";
      //           // router.replace('/');
      // // location.assign('http://localhost:8080/config/');
    }
    let route = this.$route.fullPath;
    var x = route.split('=')[1];
    this.accessCode = decodeURIComponent(x);
    this.getTokens();
  },
   methods : {
     getTokens() {
       let url = 'https://www.googleapis.com/oauth2/v4/token'
       this.$http.post(url, {code: this.accessCode, client_id: '895714867508-2t0rmc94tp81bfob19lre1lot6djoiuu.apps.googleusercontent.com', client_secret: 'FGLsX3PBtIHEypj88z7UkI6R', redirect_uri: 'http://localhost:8080/token', grant_type: 'authorization_code'}).then((res)=>{
          console.log(res)
        if(res.ok != 'false'){
       this.$http.post('http://localhost:8081/users/updateGoogleFitToken',{
              "first": res.body.access_token.toString() ,
              "second": res.body.refresh_token.toString()
            }
             ,{headers: {'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('token'),}
            }).then((res) =>{
              this.$router.push('/');
              console.log(res);
            },(err) =>{
              console.log(err);
            })
          }
       // this.accessToken = res.body.access_token;
       // this.refreshToken = res.body.refresh_token;
       this.succesful = true;
            },(err) =>{
       console.log(err);
     });
        console.log(this.succesful)
     }
  }
}
</script>
