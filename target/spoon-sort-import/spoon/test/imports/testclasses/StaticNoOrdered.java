package spoon.test.imports.testclasses;


import java.lang.annotation.Annotation;
import java.nio.charset.Charset;
import org.junit.Assert;
import org.junit.Test;

import static java.nio.charset.Charset.forName;
import static org.junit.Assert.assertEquals;


public class StaticNoOrdered {
    public void testMachin() {
        Assert.assertEquals("bla", "truc");
        Test test = new Test() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return null;
            }

            @Override
            public Class<? extends Throwable> expected() {
                return null;
            }

            @Override
            public long timeout() {
                return 0;
            }
        };
    }

    public void anotherStaticImoport() {
        Charset charset = forName("utf-8");
    }
}

