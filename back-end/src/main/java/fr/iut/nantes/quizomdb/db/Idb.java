package fr.iut.nantes.quizomdb.db;

import fr.iut.nantes.quizomdb.entite.Gamer;

/**
 * @version 1.0
 * @since 1.0
 */
public interface Idb {

    /**
     * @param pseudo name of gamer
     * @param val    number of answers
     * @return true if ok
     * @throws ExceptionDB
     * @since 1.0
     */
    boolean setAnswers(String pseudo, int val) throws ExceptionDB;

    /**
     * @param pseudo name of gamer
     * @param val    number of good answers
     * @return true if ok
     * @throws ExceptionDB
     * @since 1.0
     */
    boolean setGoodAnswers(String pseudo, int val) throws ExceptionDB;

    /**
     * @param gamer gamer
     * @return true if ok
     * @since 1.0
     */
    boolean addGamer(Gamer gamer, String pwd);

    /**
     * @param pseudo gamer
     * @return true if ok
     * @since 1.0
     */
    boolean supGamer(String pseudo);

    /**
     * @param pseudo name of gamer
     * @return number of answers
     * @throws ExceptionDB
     * @since 1.0
     */
    int getAnswers(String pseudo) throws ExceptionDB;

    /**
     * @param pseudo name of gamer
     * @return number of good answers
     * @throws ExceptionDB
     * @since 1.0
     */
    int getGoodAnswers(String pseudo) throws ExceptionDB;


    /**
     * @param login name of gamer
     * @param pwd   password of gamer
     * @return true if login and pwd is good
     * @since 1.0
     */
    boolean connect(String login, String pwd);

}

