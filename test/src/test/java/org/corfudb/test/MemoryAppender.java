package org.corfudb.test;

import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Queues;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

/** The memory appender logs messages into a in-memory circular buffer which
 *  stores a limited number of entries.
 *
 * @param <E>   The event object type.
 */
@Slf4j
public class MemoryAppender<E> extends UnsynchronizedAppenderBase<E> {

    private Queue<byte[]> queue;
    private Encoder<E> encoder;

    public MemoryAppender(int maxElements, @Nonnull Encoder<E> encoder) {
        queue = Queues.synchronizedQueue(EvictingQueue.create(maxElements));
        this.encoder = encoder;
    }


    public void reset() {
        queue.clear();
    }

    public Iterable<byte[]> getEventsAndReset() {
        List<byte[]> list = new ArrayList<>();
        byte[] element = queue.poll();
        while (element != null) {
            list.add(element);
            element = queue.poll();
        }
        return list;
    }

    @Override
    protected void append(E eventObject) {
        queue.offer(encoder.encode(eventObject));
    }
}
