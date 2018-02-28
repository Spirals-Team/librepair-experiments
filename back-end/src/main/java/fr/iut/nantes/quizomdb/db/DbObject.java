package fr.iut.nantes.quizomdb.db;

import fr.iut.nantes.quizomdb.application.QuizOmdbApplication;
import fr.iut.nantes.quizomdb.entite.Gamer;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @see Idb
 * @since 1.0
 */
public class DbObject implements Idb {

    private Map<String, Gamer> listGamers;

    public DbObject() {
        QuizOmdbApplication.log.debug("create DB Object");
        listGamers = new HashMap<>();
    }

    @Override
    public boolean setAnswers(String pseudo, int val) {
        return true;
    }

    @Override
    public boolean setGoodAnswers(String pseudo, int val) {
        return true;
    }

    @Override
    public boolean addGamer(Gamer gamer, String pwd) {
        listGamers.put(gamer.getLogin(), gamer);
        return true;
    }

    @Override
    public boolean supGamer(String pseudo) {
        listGamers.remove(pseudo);
        return true;
    }

    @Override
    public int getAnswers(String pseudo) {
        return listGamers.get(pseudo).getAnswers();
    }

    @Override
    public int getGoodAnswers(String pseudo) {
        return listGamers.get(pseudo).getGoodAnswers();
    }

    @Override
    public boolean connect(String login, String pwd) {
        return true;
    }
}
