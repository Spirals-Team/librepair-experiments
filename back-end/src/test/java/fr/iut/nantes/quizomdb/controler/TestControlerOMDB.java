package fr.iut.nantes.quizomdb.controler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.iut.nantes.quizomdb.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void  generateQuestion(){
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String json = control.generateQuestion("test");
        JsonObject jobj = gson.fromJson(json, JsonObject.class);
        assertTrue(!jobj.get("question").isJsonNull());
    }






}
