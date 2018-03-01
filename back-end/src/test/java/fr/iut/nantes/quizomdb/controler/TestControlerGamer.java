package fr.iut.nantes.quizomdb.controler;

import fr.iut.nantes.quizomdb.Utils;
import fr.iut.nantes.quizomdb.db.ExceptionDB;
import fr.iut.nantes.quizomdb.entite.Gamer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestControlerGamer {
    ControlerGamer control;

	 @Before
	 public void initialize() {
         Utils.setupConfig();
		 control = new ControlerGamer();
		 control.getDb().addGamer(new Gamer("test",0,0), "test");
	    }
	
	
	@Test
	public void testAddGamerAndDisconnect(){
		assertNull(control.getGamer("login"));
		control.addGamer("login", 0,0);
        assertNotNull(control.getGamer("login"));
        control.disconnect("login");
        assertNull(control.getGamer("login"));
	}

    @Test
    public void testGetLogins(){
        assertTrue(!control.getLogins().contains("login"));
        control.addGamer("login", 0,0);
        assertTrue(control.getLogins().contains("login"));

    }

    @Test (expected = Exception.class)
    public void testLoginInvalid() throws Exception {
        control.login("falseLogin", "falsePassword");
    }

    @Test
    public void testAddGamerReturn() throws Exception {
        String value1 = control.addGamer("test", 0, 0);
        String value2 = control.addGamer("test", 0, 0);
        assertTrue(value1.equals(value2));
    }

    @Test
    public void testDisconnectChangeValue(){
        String pseudo = "test";
        int valBd = 0;
        try {
            valBd = control.getDb().getAnswers(pseudo);
            control.addGamer(pseudo, -1, 0);
            int valLocal = control.getGamer(pseudo).getAnswers();
            assertTrue(valBd==valLocal);
            control.getGamer(pseudo).incrementAnswers();
            valBd = control.getDb().getAnswers(pseudo);
            assertTrue(valBd!=valLocal);
            control.disconnect(pseudo);
            valBd = control.getDb().getAnswers(pseudo);
            assertTrue(valBd==valLocal);
        } catch (ExceptionDB exceptionDB) {
            exceptionDB.printStackTrace();
        }

    }

}
