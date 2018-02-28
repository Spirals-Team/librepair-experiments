package fr.iut.nantes.quizomdb.entite;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by E155722N on 22/02/18.
 */
public class GamerTest {
    Gamer gamer;

    @Before
    public void initialize() {
        gamer = new Gamer("login", 0,0);
    }

    @Test
    public void getLogin() throws Exception {
        assertEquals("login", gamer.getLogin());
    }

    @Test
    public void getGoodAnswers() throws Exception {
        Gamer gamer2 = new Gamer("login", 0,1);
        assertEquals(0, gamer2.getGoodAnswers());
    }

    @Test
    public void incrementGoodAnswers() throws Exception {
        int nb = gamer.getGoodAnswers();
        nb++;
        gamer.incrementGoodAnswers();
        assertEquals(nb, gamer.getGoodAnswers());
    }

    @Test
    public void getAnswers() throws Exception {
        Gamer gamer2 = new Gamer("login", 0,1);
        assertEquals(1, gamer2.getAnswers());
    }

    @Test
    public void incrementAnswers() throws Exception {
        int nb = gamer.getAnswers();
        nb++;
        gamer.incrementAnswers();
        assertEquals(nb, gamer.getAnswers());
    }

    @Test
    public void equals() throws Exception {
        Gamer gamer2 = new Gamer("login", 0,1);
        Gamer gamer3 = new Gamer("login", 1,0);
        assertTrue(gamer2.equals(gamer3));
    }

    @Test
    public void notEquals() throws Exception {
        Gamer gamer2 = new Gamer("login", 0,1);
        Gamer gamer3 = new Gamer("log", 1,0);
        assertTrue(!gamer2.equals(gamer3));
    }

}