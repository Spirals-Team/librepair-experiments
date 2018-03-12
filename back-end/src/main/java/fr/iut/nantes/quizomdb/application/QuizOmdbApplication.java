package fr.iut.nantes.quizomdb.application;

import fr.iut.nantes.quizomdb.controler.ControlerGeneral;
import fr.iut.nantes.quizomdb.entite.Config;
import fr.iut.nantes.quizomdb.entite.TokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Main class
 *
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@Controller
@CrossOrigin
@RequestMapping("/")
public class QuizOmdbApplication extends SpringBootServletInitializer {

    /**
     * log of application
     *
     * @since 1.0
     */
    public static final Logger log = LoggerFactory.getLogger("QuizOmdb");
    public static Config config;
    ControlerGeneral control;

    public QuizOmdbApplication() {
        if (config == null) {
            String configPath = QuizOmdbApplication.class.getResource("/config.properties").getPath();
            log.info("path of config file: " + configPath);
            config = new Config(configPath);
        }
        control = new ControlerGeneral();
    }

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

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(QuizOmdbApplication.class);
    }

    @GetMapping(value = "/question",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    ResponseEntity generateQuestion(HttpServletRequest request) {
        try {
            String token = request.getParameter("token");
            ResponseEntity res = ResponseEntity.ok(control.generateQuestion(token));
            return res;
        }catch (TokenException e) {
            return e.getResponseEntity();
        }catch (Exception e) {
            String json = "{ \"error\" : \" Error not defined\" }";
            return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/response",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    ResponseEntity sendResponse(@RequestParam("response") String response, HttpServletRequest request) {
        try {
            String token = request.getParameter("token");
            ResponseEntity res = ResponseEntity.ok("{ " +
                    "\"result\" : \"" + control.isCorrectResponse(token, response)
                    + "\" }");
            return res;
        }catch (TokenException e) {
            return e.getResponseEntity();
        }catch (Exception e) {
            String json = "{ \"error\" : \" Error not defined\" }";
            return new ResponseEntity<>(json,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/login",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    ResponseEntity login(HttpServletRequest request) {
        try {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            ResponseEntity res = ResponseEntity.ok("{ " +
                    "\"token\" : \"" + control.login(login, password)
                    + "\" }");
            return res;
        } catch (Exception e) {
            String json = "{ \"error\" : \" Login or password invalid\" }";
            return new ResponseEntity<>(json, HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping(value = "/disconnect",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    ResponseEntity disconnect(HttpServletRequest request) {
        try {
            String token = request.getParameter("token");
            control.disconnect(token);
            ResponseEntity res = ResponseEntity.ok("{ \"result\" : \"ok\" }");
            return res;
        } catch (TokenException e) {
            return e.getResponseEntity();
        }
    }

    @GetMapping(value = "/goodanswers",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    ResponseEntity getGoodAnswers(HttpServletRequest request) {
        try {
            String token = request.getParameter("token");
            ResponseEntity res = ResponseEntity.ok("{ " +
                    "\"goodanswers\" : \"" + control.getGoodAnswers(token)
                    + "\" }");
            return res;
        }catch (TokenException e) {
            return e.getResponseEntity();
        }
    }

    @GetMapping(value = "/answers",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    ResponseEntity getAnswers(HttpServletRequest request) {
        try {
            String token = request.getParameter("token");
            ResponseEntity res = ResponseEntity.ok("{ " +
                    "\"answers\" : \"" + control.getAnswers(token)
                    + "\" }");
            return res;
        } catch (TokenException e) {
            return e.getResponseEntity();
        }
    }

    @RequestMapping("/")
    @ResponseBody
    ResponseEntity index() {
        return ResponseEntity.ok("{ \"server\" : \"OK\" }");
    }

    @PostMapping(value = "/register",
            produces = "application/json; charset=utf-8")
    @ResponseBody
    ResponseEntity register(HttpServletRequest request) {
        try {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            ResponseEntity res = ResponseEntity.ok(control.createAccount(login, password));
            return res;
        } catch (Exception e) {
            String json = "{ \"error\" : \" login already used\" }";
            return new ResponseEntity<>(json, HttpStatus.CONFLICT);
        }
    }

}
