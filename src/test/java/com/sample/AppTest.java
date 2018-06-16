package com.sample;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppTest {


    @Test
    public void testSum() {
        App app = new App();
        assertEquals(app.sum(1, 1), 2);
        assertEquals(app.sum(0, 0), 0);
        assertEquals(app.sum(-1, -2), -3);
        assertEquals(app.sum(0, -2), -2);
        assertEquals(app.sum(5, -2), 4);
    }

}
