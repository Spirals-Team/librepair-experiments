package com.reubentjl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FooTest {

    @Test
    public void testToString() {
        String expected = "Test";
        Foo foo = new Foo(expected);
        Assertions.assertEquals(expected, foo.toString());
    }

    @Test
    public void testToStringFailure() {
        String expected = "123";
        Foo foo = new Foo("Abc");
        Assertions.assertEquals(expected, foo.toString());
    }

}
