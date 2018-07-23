import org.junit.Test;

import static org.junit.Assert.*;

public class AutomataTest {

    private Automata a;

    public AutomataTest() {
        a = new Automata();
    }

    @org.junit.Test
    public void off() {
        a.off();
        assertEquals(STATES.OFF, a.printState());
    }

    @org.junit.Test
    public void coin() {
        a.on();
        a.coin(13);
        assertEquals(STATES.ACCEPT, a.printState());
        assertEquals(13.0f, a.getCash(), 0.1f);
    }

    @org.junit.Test
    public void choice() {
        a.on();
        a.printMenu();
        a.coin(10);
        a.choice("Fuzz-Tea");
        assertEquals(STATES.CHECK, a.printState());
        assertEquals("Fuzz-Tea", a.choice("Fuzz-Tea"));
    }


    @org.junit.Test
    public void cancel() {
        a.cancel();
        assertEquals(STATES.WAIT, a.printState());
    }


    @org.junit.Test
    public void finish() {
        a.finish();
        assertEquals(STATES.WAIT, a.printState());
    }

    @Test
    public void on() {
        a.on();
        assertEquals(STATES.WAIT, a.printState());
    }

    @Test
    public void getCash() {
        a.on();
        a.coin(10);
        a.choice("Fuzz-Tea");
        a.cook();
        assertEquals(8.5f, a.getCash(), 0.1f);
    }

    @Test
    public void cook() {
        a.on();
        a.coin(10);
        a.choice("Fuzz-Tea");
        assertEquals("Fuzz-Tea", a.cook());
    }
}