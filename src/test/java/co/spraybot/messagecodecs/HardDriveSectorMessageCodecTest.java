package co.spraybot.messagecodecs;

import co.spraybot.HardDriveSector;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import io.vertx.core.eventbus.MessageCodec;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HardDriveSectorMessageCodecTest extends MessageCodecTest<HardDriveSector> {
    @Override
    protected MessageCodec<HardDriveSector, HardDriveSector> subject() {
        return new HardDriveSectorCodec();
    }

    @Override
    protected HardDriveSector transformEntity() {
        HardDriveSector hardDriveSector = mock(HardDriveSector.class);
        when(hardDriveSector.identifier()).thenReturn("foo");
        when(hardDriveSector.cellContents()).thenReturn("bar");
        return hardDriveSector;
    }

    @Override
    protected void assertDecodedEntity(HardDriveSector subject) {
        assertEquals("foo", subject.identifier());
        assertEquals("bar", subject.cellContents());
    }

}
