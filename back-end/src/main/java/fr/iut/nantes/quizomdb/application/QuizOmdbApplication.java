package fr.iut.nantes.quizomdb.application;

import fr.iut.nantes.quizomdb.controler.ControlerGeneral;
import fr.iut.nantes.quizomdb.entite.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Main class
 *
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@Controller
public class QuizOmdbApplication extends SpringBootServletInitializer {

    /**
     * log of application
     *
     * @since 1.0
     */
    public static final Logger log = LoggerFactory.getLogger("QuizOmdb");
    public static Config config;
    ControlerGeneral control = new ControlerGeneral();

    /**
     * @param args argument of application
     * @since 1.0
     */
    public static void main(String[] args) {
        String configPath = args.length > 0 ?
                QuizOmdbApplication.class.getResource(args[0]).getPath() :
                QuizOmdbApplication.class.getResource("/config.properties").getPath();
        log.info("path of config file: " + configPath);
        config = new Config(configPath);
        SpringApplication.run(QuizOmdbApplication.class, args);
    }


    @RequestMapping("/question")
    @ResponseBody
    String generateQuestion(String token) {
        return control.generateQuestion(token);
    }

    @RequestMapping("/response")
    @ResponseBody
    String sendResponse(String token, @RequestParam("response") String response) {

        return "{ " +
                "\"result\" : \"" + control.isCorrectResponse(token, response)
                + "\" }";
    }

    @RequestMapping("/login")
    @ResponseBody
    String login(String login, String password) {
        return "Your token : " + control.login(login, password);
    }

    @RequestMapping("/disconnect")
    @ResponseBody
    String disconnect(String token) {
        control.disconnect(token);
        return "You have been disconnected.";
    }

    @RequestMapping("/goodanswers")
    @ResponseBody
    String getGoodAnswers(String token) {
        return "Number of good answers : " + control.getGoodAnswers(token);
    }

    @RequestMapping("/answers")
    @ResponseBody
    String getAnswers(String token) {
        return "Number of answers : " + control.getAnswers(token);
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "<h1>Welcome !</h1>";
    }

    @RequestMapping("/register")
    @ResponseBody
    String register() {
        return "<h1>Inscription page</h1>";
    }

    @RequestMapping("/quizz")
    @ResponseBody
    String quizz() {
        return "<h1>Quizz page</h1>";
    }


}
