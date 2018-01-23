package server.controllers;

import org.junit.*;

import static org.junit.Assert.*;

public class BrukerControllerTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getBrukernavn() throws Exception {
        assertEquals("bruker1", BrukerController.getNavn(1));
    }

    @Test
    public void getEpost() throws Exception {
        assertEquals("bruker1@mail.no", BrukerController.getEpost(1));
    }

    @Test
    public void getFavoritthusholdning() throws Exception {
        assertEquals("1", BrukerController.getFavoritthusholdning(1));
    }

    @Test
    public void loginOk() throws Exception {
    }
}