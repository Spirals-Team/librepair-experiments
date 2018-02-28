package fr.iut.nantes.quizomdb.controler;

import fr.iut.nantes.quizomdb.Utils;
import fr.iut.nantes.quizomdb.entite.Constants;
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
        token = gamer.addGamer("login", 0,0);

    }

    @Test
    public void getLoginFromToken(){
        assertNull(control.getLoginFromToken(""));
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

    //TODO reste des requetes en attente de la BD













}
