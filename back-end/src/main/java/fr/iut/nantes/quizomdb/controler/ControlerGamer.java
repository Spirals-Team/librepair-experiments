package fr.iut.nantes.quizomdb.controler;

import fr.iut.nantes.quizomdb.db.DbMongo;
import fr.iut.nantes.quizomdb.db.ExceptionDB;
import fr.iut.nantes.quizomdb.db.Idb;
import fr.iut.nantes.quizomdb.entite.Constants;
import fr.iut.nantes.quizomdb.entite.Gamer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Set;

/**
 * @version 1.0
 * @since 1.0
 */
public class ControlerGamer {

    private HashMap<String, Gamer> gamers;
    private Idb db;

    /**
     * @version 1.0
     * @see ControlerGamer
     * @since 1.0
     */
    public ControlerGamer() {
        this.gamers = new HashMap<>();
        try {
            this.db = new DbMongo();
        } catch (ExceptionDB exceptionDB) {
            exceptionDB.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Try to authenticate the user
     *
     * @param login    gamer
     * @param password gamer
     * @return the token of the user
     * @throws Exception
     * @since 1.0
     */
    public String login(String login, String password) throws Exception {
        if (!db.connect(login, password)) {
            throw new Exception();
        }
        return addGamer(login, db.getAnswers(login), db.getGoodAnswers(login));
    }

    /**
     * Add a user in the local memory
     *
     * @param login
     * @param answers
     * @param goodAnswers
     * @return the token of the user
     */
    public String addGamer(String login, int answers, int goodAnswers) {
        Gamer gamer = new Gamer(login, answers, goodAnswers);
        this.gamers.put(login, gamer);
        return Jwts.builder()
                .setSubject(login)
                .signWith(SignatureAlgorithm.HS512, Constants.key)
                .compact();
    }

    /**
     * Delete the user from the local memory
     *
     * @param login of the user
     * @since 1.0
     */
    public void disconnect(String login) {
        try {
            this.db.setAnswers(login, this.gamers.get(login).getAnswers());
            this.db.setGoodAnswers(login, this.gamers.get(login).getGoodAnswers());
        } catch (ExceptionDB exceptionDB) {
            exceptionDB.printStackTrace();
        }
        this.gamers.remove(login);
    }


    /**
     * @param login of the user
     * @return a user
     * @since 1.0
     */
    public Gamer getGamer(String login) {
        return this.gamers.get(login);
    }

    /**
     * @return the gamers in the local memory
     * @since 1.0
     */
    public Set<String> getLogins() {
        return this.gamers.keySet();
    }


    public Idb getDb() {
        return db;
    }

}
