package fr.iut.nantes.quizomdb;

import fr.iut.nantes.quizomdb.application.QuizOmdbApplication;
import fr.iut.nantes.quizomdb.entite.Config;

import static fr.iut.nantes.quizomdb.application.QuizOmdbApplication.config;
import static fr.iut.nantes.quizomdb.application.QuizOmdbApplication.log;

/**
 * Created by Paul on 27/02/18.
 */
public class Utils {
    public static void setupConfig() {
        String configPath = QuizOmdbApplication.class.getResource("/configTest.properties").getPath();
        log.info("path of config file: " + configPath);
        config = new Config(configPath);
    }
}
