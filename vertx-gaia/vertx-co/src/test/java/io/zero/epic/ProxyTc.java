package io.zero.epic;

import io.vertx.ext.unit.TestContext;
import io.zero.quiz.ZeroBase;
import org.junit.Test;

interface Hello {
    void sayHello();
}

public class ProxyTc extends ZeroBase {
    @Test
    public void testInvoke(final TestContext context) {
        final Object proxy = Fantam.getProxy(Hello.class);
        Ut.invoke(proxy, "sayHello");
    }
}