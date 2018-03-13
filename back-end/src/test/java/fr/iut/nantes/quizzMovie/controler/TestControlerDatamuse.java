package fr.iut.nantes.quizzMovie.controler;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @version 1.0
 * @see ControlerDatamuse
 * @since 1.0
 */
public class TestControlerDatamuse {

    ControlerDatamuse control;

    /**
     * @since 1.0
     */
    @Before
    public void initialize() {
        control = new ControlerDatamuse();
    }

    /**
     * @throws Exception
     * @see ControlerDatamuse#hasCloseSpelling(String, String)
     * @since 1.0
     */
    @Test
    public void hasCloseSpelling1() throws Exception {
        String proposed = "response", response = "response";
        assertTrue(control.hasCloseSpelling(proposed, response));

    }

    /**
     * @throws Exception
     * @see ControlerDatamuse#hasCloseSpelling(String, String)
     * @since 1.0
     */
    @Test
    public void hasCloseSpelling2() throws Exception {
        String proposed = "respons", response = "response";
        assertFalse(control.hasCloseSpelling(proposed, response));

    }


    /**
     * @throws Exception
     * @see ControlerDatamuse#hasCloseSpelling(String, String)
     * @since 1.0
     */
    @Test
    public void hasCloseSpelling3() throws Exception {
        String proposed = "response answers", response = "response response";
        assertFalse(control.hasCloseSpelling(proposed, response));

    }


    /**
     * @throws Exception
     * @see ControlerDatamuse#hasCloseSpelling(String, String)
     * @since 1.0
     */
    @Test
    public void hasCloseSpelling4() throws Exception {
        String proposed = "lagend", response = "legend";
        assertTrue(control.hasCloseSpelling(proposed, response));

    }
}