package io.descoped.client.api.test.lambda;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class LambdaHandlerProcessorProducerTest {

    private static final Logger log = LoggerFactory.getLogger(LambdaHandlerProcessorProducerTest.class);

    class Singleton<T> {
        private Handler<T> handler;

        public Singleton() {
        }

        public Singleton<T> handle(Handler<T> handler) {
            this.handler = handler;
            return this;
        }

        public Processor<T> produce() {
            return handler.apply(Optional.of("This is payload!"));
        }
    }

    interface Processor<T> {
        T value();

        static <U> Processor<U> asValueProcessor(U value) {
            return new ValueProcessor<>(value);
        }
    }

    static class ValueProcessor<T> implements Processor<T> {
        private final T value;

        public ValueProcessor(T value) {
            this.value = value;
        }

        @Override
        public T value() {
            return value;
        }
    }

    @FunctionalInterface
    interface Handler<T> {
        Processor<T> apply(Optional<String> payload);

        static Handler<String> asString() {
            return payload -> Processor.asValueProcessor(payload.get());
        }

        static Handler<byte[]> asBytes() {
            return payload -> Processor.asValueProcessor(payload.orElse("").getBytes());
        }
    }

    @Test
    public void testProcessors() {
        Handler<String> stringHandler = Handler.asString();
        String string = new Singleton<String>().handle(stringHandler).produce().value();
        byte[] bytes = new Singleton<byte[]>().handle(Handler.asBytes()).produce().value();
        assertEquals(string, new String(bytes, StandardCharsets.UTF_8));
        log.trace("produced string value: {}", string);
        log.trace("produced byte[] value: {}", bytes);
    }

}
