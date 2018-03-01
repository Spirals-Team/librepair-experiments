package fr.iut.nantes.quizomdb.controler;

import fr.iut.nantes.quizomdb.Utils;
import fr.iut.nantes.quizomdb.entite.Constants;
import fr.iut.nantes.quizomdb.entite.Gamer;
import io.jsonwebtoken.Jwts;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestControlerGeneral {
    ControlerGeneral control;
    String token ;

    @Before
    public void initialize() {
        Utils.setupConfig();
        control = new ControlerGeneral();
        ControlerGamer gamer = new ControlerGamer();
        gamer.getDb().addGamer(new Gamer("test",0,0), "test");
        token = gamer.addGamer("login", 0,0);
    }

    @Test
    public void getLoginFromToken(){
        assertNull(control.getLoginFromToken(""));
    }

    @Test
    public void getLoginFromToken2(){
        control.login("test", "test");
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0In0.HPr7JiM6oXhk7s1xH_pTV5-yAuc41cXACldKTzBj66gEo7ArMKx3kCESEt7eWfXjiH3rEmjabLCMxS9_Cwm23Q";
        assertEquals("login",control.getLoginFromToken(token));
    }

    @Test
    public void isValideToken(){
        assertTrue(Jwts.parser().setSigningKey(Constants.key).parseClaimsJws(token).getBody().getSubject().equals("login"));
        assertTrue(!Jwts.parser().setSigningKey(Constants.key).parseClaimsJws(token).getBody().getSubject().equals("log"));
    }

    @Test
    public void loginFailed(){
        assertEquals("err", control.login("",""));
    }













}
