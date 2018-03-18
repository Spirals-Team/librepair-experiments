package co.spraybot.messagecodecs;

import co.spraybot.ChatMessage;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import io.vertx.core.eventbus.MessageCodec;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public abstract class MessageCodecTest<T> {

    protected abstract MessageCodec<T, T> subject();

    protected abstract T transformEntity();

    protected abstract void assertDecodedEntity(T entity);

    @Test
    public void systemCodeIsNegativeOne() {
        assertEquals(-1, subject().systemCodecID());
    }

    @Test
    public void nameIsSimpleClassName() {
        MessageCodec<T, T> subject = subject();

        assertEquals(subject.getClass().getSimpleName(), subject.name());
    }

    @Test
    public void transformIsSameObject() {
        T entity = transformEntity();
        MessageCodec<T, T> subject = subject();

        assertSame(entity, subject.transform(entity));
    }

    @Test
    public void decodeFromWire() {
        Buffer buffer = new BufferImpl();

        MessageCodec<T, T> subject = subject();
        subject.encodeToWire(buffer, transformEntity());

        T entity = subject.decodeFromWire(0, buffer);

        assertDecodedEntity(entity);
    }

}
