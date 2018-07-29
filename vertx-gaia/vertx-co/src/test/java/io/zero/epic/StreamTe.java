package io.zero.epic;

import io.vertx.ext.unit.TestContext;
import io.vertx.zero.exception.heart.EmptyStreamException;
import io.zero.quiz.StoreBase;
import org.junit.Test;

public class StreamTe extends StoreBase {

    @Test(expected = EmptyStreamException.class)
    public void testRead(final TestContext context) {
        context.assertNotNull(Stream.read(this.getFile("ini.txt")));
    }
}
