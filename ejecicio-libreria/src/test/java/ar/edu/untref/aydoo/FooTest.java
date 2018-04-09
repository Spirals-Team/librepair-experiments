package ar.edu.untref.aydoo;

import org.junit.Test;
import org.junit.Assert;


public class FooTest 
{
    @Test
    public void doFooShouldReturnFoo()
    {
        Foo foo = new Foo();
        String result = foo.doFoo();
        Assert.assertEquals("foo", result);
    }
}
