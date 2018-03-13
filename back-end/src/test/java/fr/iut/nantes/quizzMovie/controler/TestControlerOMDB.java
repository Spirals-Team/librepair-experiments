package fr.iut.nantes.quizzMovie.controler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.iut.nantes.quizzMovie.Utils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @version 1.0
 * @see ControlerOMDB
 * @since 1.0
 */
public class TestControlerOMDB {

    ControlerOMDB control;

    /**
     * @since 1.0
     */
    @Before
    public void initialize() {
        Utils.setupConfig();
        control = new ControlerOMDB();
    }


    /**
     * @see ControlerOMDB#addQuizz(String, String, String)
     * @see ControlerOMDB#disconnect(String)
     * @since 1.0
     */
    @Test
    public void disconnectAndAddQuizz() {
        assertNull(control.getQuestion("login"));
        control.addQuizz("login", "question", "answers");
        assertNotNull(control.getQuestion("login"));
        control.disconnect("login");
        assertNull(control.getQuestion("login"));
    }

    /**
     * @see ControlerOMDB#addQuizz(String, String, String)
     * @since 1.0
     */
    @Test
    public void getAnswers() {
        control.addQuizz("login", "question", "answers");
        assertEquals("answers", control.getAnswers("login"));
        control.addQuizz("login2", "question2", "answers2");
        assertEquals("answers2", control.getAnswers("login2"));
    }

    /**
     * @see ControlerOMDB#generateQuestion(String)
     * @since 1.0
     */
    @Test
    public void generateQuestion() {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String json = control.generateQuestion("test");
        JsonObject jobj = gson.fromJson(json, JsonObject.class);
        assertTrue(!jobj.get("question").isJsonNull());
    }


}
