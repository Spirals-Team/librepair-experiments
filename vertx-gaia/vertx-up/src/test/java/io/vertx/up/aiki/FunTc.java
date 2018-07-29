package io.vertx.up.aiki;

import org.junit.Test;

public class FunTc {

    @Test
    public void testDirect() {
        Ux.log(this.getClass()).info("Hello");
    }

    @Test
    public void testDirectLines() {
        Ux.log(this.getClass()).info("Hello", "World");
    }

    @Test
    public void testPattern() {
        Ux.log(this.getClass()).on("[ ZERO ] {0}").info("Lang");
    }
}
