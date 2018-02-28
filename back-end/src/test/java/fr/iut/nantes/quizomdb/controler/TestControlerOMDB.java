package fr.iut.nantes.quizomdb.controler;

import fr.iut.nantes.quizomdb.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestControlerOMDB {

    ControlerOMDB control;

    @Before
    public void initialize() {
        Utils.setupConfig();
        control = new ControlerOMDB();
    }


    @Test
    public void disconnectAndAddQuizz(){
        assertNull(control.getQuestion("login"));
        control.addQuizz("login","question","answers");
        assertNotNull(control.getQuestion("login"));
        control.disconnect("login");
        assertNull(control.getQuestion("login"));
    }

    @Test
    public void getAnswers(){
        control.addQuizz("login","question","answers");
        assertEquals("answers", control.getAnswers("login"));
        control.addQuizz("login2","question2","answers2");
        assertEquals("answers2", control.getAnswers("login2"));
    }


    // TODO generateQuestion()






}
