package fr.iut.nantes.quizzMovie.controler;

import fr.iut.nantes.quizzMovie.Utils;
import fr.iut.nantes.quizzMovie.entite.Constants;
import fr.iut.nantes.quizzMovie.entite.Gamer;
import fr.iut.nantes.quizzMovie.entite.TokenException;
import io.jsonwebtoken.Jwts;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @version 1.0
 * @see ControlerGeneral
 * @since 1.0
 */
public class TestControlerGeneral {

    ControlerGeneral control;
    String token;
    ControlerGamer gamer;

    /**
     * @since 1.0
     */
    @Before
    public void initialize() throws Exception {
        Utils.setupConfig();
        control = new ControlerGeneral();
        gamer = new ControlerGamer();
        gamer.getDb().addGamer(new Gamer("test", 0, 0), "test");
        token = control.login("test", "test");

    }

    /**
     * @throws Exception
     * @since 1.0
     */
    @After
    public void tearDown() throws Exception {
        gamer.getDb().supGamer("test");
    }

    /**
     * @see ControlerGeneral#getLoginFromToken(String)
     * @since 1.0
     */
    @Test(expected = Exception.class)
    public void getLoginFromToken() throws TokenException {
        control.getLoginFromToken("");
    }

    /**
     * @see ControlerGeneral#getLoginFromToken(String)
     * @since 1.0
     */
    @Test
    public void getLoginFromToken2() throws TokenException {
        assertEquals("test", control.getLoginFromToken(token));
    }

    /**
     * @since 1.0
     */
    @Test
    public void isValideToken() {
        assertTrue(Jwts.parser().setSigningKey(Constants.key).parseClaimsJws(token).getBody().getSubject().equals("test"));
        assertFalse(Jwts.parser().setSigningKey(Constants.key).parseClaimsJws(token).getBody().getSubject().equals("log"));
    }

    /**
     * @see ControlerGeneral#login(String, String)
     * @since 1.0
     */
    @Test (expected = Exception.class)
    public void loginFailed() throws Exception {
        control.login("", "");
    }


}
