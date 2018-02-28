package fr.iut.nantes.quizomdb.controler;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by E155722N on 28/02/18.
 */
public class TestControlerDatamuse {

    ControlerDatamuse control;

    @Before
    public void initialize() {
        control = new ControlerDatamuse();
    }

    @Test
    public void hasCloseSpelling1() throws Exception {
        String response = "response", answers = "response";
        assertTrue(control.hasCloseSpelling(response, answers));

    }

    @Test
    public void hasCloseSpelling2() throws Exception {
        String response = "respons", answers = "response";
        assertTrue(!control.hasCloseSpelling(response, answers));

    }

    @Test
    public void hasCloseSpelling3() throws Exception {
        String response = "response answers", answers = "response response";
        assertTrue(!control.hasCloseSpelling(response, answers));

    }

    @Test
    public void hasCloseSpelling4() throws Exception {
        String response = "legend", answers = "lagend";
        assertTrue(control.hasCloseSpelling(response, answers));

    }



}