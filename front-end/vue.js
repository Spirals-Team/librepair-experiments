var adress = "http://lp-miar-groupe01-cloned-paul604.c9users.io/quiz-omdb-1.0-SNAPSHOT";

var vue = new Vue({
  el: '#vue',
  data: {
    body: "loginTemp",
    precedentPage: "loginTemp",
    score: 0,
    totalScore: 0,
    totalQuestion: 0,
    login: "",
    password: "",
    token: "",
    questionPicture: "",
    question: "",
    answer: ""
  },
  computed: {
    successRate: function() {
      return (this.totalQuestion == 0) ? 0 : ((this.score/this.totalQuestion)*100);
    }
  },
  methods: {
    toStats: function() {
      this.httpGetAsync(adress+"/answers?token="+this.token, function(json){
        var jsonObject = JSON.parse(json);
        this.totalQuestion = parseInt(jsonObject.answers);
        this.httpGetAsync(adress+"/goodanswers?token="+this.token, function(json){
          var jsonObject = JSON.parse(json);
          this.totalScore = parseInt(jsonObject.goodanswers);
          this.body = "statsTemp";
        }.bind(this));
      }.bind(this));
    },
    loginToQuiz: function() {
      this.connection(function(json) {
        this.token = JSON.parse(json).token;
        this.toQuiz();
      }.bind(this));
    },
    loginToRegister: function() {
      this.body = "registerTemp";
    },
    toLogin: function() {
      this.resetConnection();
      this.body = "loginTemp";
    },
    registerToQuiz: function() {
      this.register(function(json) {
        this.body = "loginTemp";
      }.bind(this));
    },
    toRegister: function() {
      this.disconnection();
      this.body = "registerTemp";
    },
    toQuiz: function() {
      this.getQuestion();
      this.body = "quizTemp";
    },
    toLegal: function() {
      if(this.body != "legalTemp") {
        this.precedentPage = this.body;
        this.body = "legalTemp";
      }
    },
    toPrecedent: function() {
      this.body = this.precedentPage
    },
    disconnection: function(){
      this.httpPostAsync(adress+"/disconnect?token="+this.token, "", this.resetConnection.bind(this));
    },
    resetConnection: function() {
      this.login = "";
      this.password = "";
      this.token = "";
      this.score = 0;
      this.totalScore = 0;
      this.totalQuestion = 0;
    },
    connection: function(callback) {
      var params = "?login="+this.login+"&password="+this.password;
      this.httpPostAsync(adress+"/login"+params, "", callback);
    },
    register: function(callback) {
      var params = "?login="+this.login+"&password="+this.password;
      this.httpPostAsync(adress+"/register"+params, "", callback);
    },
    answerQuestion: function() {
      var params = "";
      this.httpPostAsync(adress+"/response?token="+this.token+"&response="+this.answer, params, function(json) {
        var jsonObject = JSON.parse(json);
        if(parseBoolean(jsonObject.result)) {
          this.score ++;
          this.totalScore ++;
        }
        this.getQuestion();
        this.totalQuestion ++;
      }.bind(this));
    },
    getQuestion: function() {
      this.httpGetAsync(adress+"/question?token="+this.token, function(json) {
        var jsonObject = JSON.parse(json);
        this.question = "<b>Question: </b>"+jsonObject.question;
        this.questionPicture = jsonObject.poster;
      }.bind(this));
    },
    httpGetAsync: function(theUrl, callback) {
      var xmlHttp = new XMLHttpRequest();
      xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
          callback(xmlHttp.responseText);
        }
      }
      xmlHttp.open("GET", theUrl, true);
      xmlHttp.send(null);
    },
    httpPostAsync: function(theUrl, params, callback) {
      var xmlHttp = new XMLHttpRequest();
      xmlHttp.onreadystatechange = function() {
      	if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {
      		callback(xmlHttp.responseText);
      	}
      }
      xmlHttp.open("POST", theUrl, true);

      //xmlHttp.setRequestHeader("Content-length", params.length);

      xmlHttp.send(params);
    }
  }
});
