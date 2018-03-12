package fr.iut.nantes.quizomdb.db;

import fr.iut.nantes.quizomdb.application.QuizOmdbApplication;

/**
 * @version 1.0
 * @since 1.0
 */
public class ExceptionDB extends Exception {

    /**
     * @param message desc of error
     * @since 1.0
     */
    public ExceptionDB(String message) {
        super(message);
        QuizOmdbApplication.log.error("ExceptionDB: " + message);
    }
}
