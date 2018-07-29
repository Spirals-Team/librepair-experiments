package io.zero.epic;

import io.vertx.zero.exception.heart.EmptyStreamException;
import io.vertx.zero.exception.heart.JsonFormatException;
import io.zero.quiz.StoreBase;
import org.junit.Test;

public class StoreTe extends StoreBase {

    @Test(expected = EmptyStreamException.class)
    public void testNone() {
        this.execJObject("zero.json", config -> {

        });
    }

    @Test(expected = JsonFormatException.class)
    public void testEmpty() {
        this.execJObject("format.json", config -> {

        });
    }
}
