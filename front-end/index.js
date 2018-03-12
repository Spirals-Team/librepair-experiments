var adress = "https://lp-miar-groupe01-cloned-paul604.c9users.io/";

var index = new Vue({
  el: '#index',
  data: {
    body: "loginTemp",
    precedentPage: "loginTemp",
    score: 0,
    totalScore: 0,
    totalQuestion: 0,
    login: "",
    password: "",
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
      this.body = "statsTemp"
    },
    toQuiz: function() {
      this.getQuestion();
      this.body = "quizTemp";
    },
    toLogin: function() {
      this.resetConnection();
      this.body = "loginTemp";
    },
    toRegister: function() {
      this.resetConnection();
      this.body = "registerTemp";
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
    resetConnection: function() {
      this.login = "";
      this.password = "";
      this.score = 0;
      this.totalScore = 0;
      this.totalQuestion = 0;
    },
    answerQuestion: function() {
      this.getQuestion();
      this.score ++;
      this.totalScore ++;
      this.totalQuestion ++;
    },
    getQuestion: function() {
      this.httpGetAsync(adress+"/question", function(json) {
        var jsonObject = JSON.parse(json);
        this.question = "<b>Question: </b>"+jsonObject.question;
        this.questionPicture = jsonObject.image;
      });
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
    }
  }
});
