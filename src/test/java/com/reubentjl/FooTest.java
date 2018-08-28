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
        String expected = "Abc";
        Foo foo = new Foo("123");
        Assertions.assertEquals(expected, foo.toString());
    }

}
