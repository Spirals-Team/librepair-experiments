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
    question: "<b>Question :</b>Explicabo non iure non quasi doloremque. Deleniti sit rerum repellat cumque in ?",
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
      if(this.questionPicture === "") {
        this.questionPicture = "http://vignette2.wikia.nocookie.net/pingufan/images/2/2e/Pingu_the_movie_xxlg.png/revision/latest?cb=20170221164619";
        this.question = "NOOT NOOT ?"
      } else {
        this.questionPicture = "";
        this.question = "<b>Question :</b>Explicabo non iure non quasi doloremque. Deleniti sit rerum repellat cumque in ?"
      }
    }
  }
})
