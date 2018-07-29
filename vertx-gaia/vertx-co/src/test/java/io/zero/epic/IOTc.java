package io.zero.epic;

import io.vertx.ext.unit.TestContext;
import io.zero.quiz.StoreBase;
import org.junit.Test;

public class IOTc extends StoreBase {
    @Test
    public void testProp(final TestContext context) {
        context.assertNotNull(IO.getProp(this.getFile("in.properties")));
    }

    @Test
    public void testYaml(final TestContext context) {
        context.assertNotNull(IO.getYaml(this.getFile("test.yaml")));
    }
}
