package fr.iut.nantes.quizzMovie;

import fr.iut.nantes.quizzMovie.application.QuizzMovieApplication;
import fr.iut.nantes.quizzMovie.entite.Config;

import static fr.iut.nantes.quizzMovie.application.QuizzMovieApplication.config;
import static fr.iut.nantes.quizzMovie.application.QuizzMovieApplication.log;

/**
 * @version 1.0
 * @since 1.0
 */
public class Utils {
    public static void setupConfig() {
        String configPath = QuizzMovieApplication.class.getResource("/configTest.properties").getPath();
        log.info("path of config file: " + configPath);
        config = new Config(configPath);
    }
}
