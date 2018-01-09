package org.corfudb.test;

import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Queues;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;

/** The memory appender logs messages into a in-memory circular buffer which
 *  stores a limited number of entries.
 *
 * @param <E>   The event object type.
 */
@Slf4j
public class MemoryAppender<E> extends UnsynchronizedAppenderBase<E> {

    private Queue<E> queue;

    public MemoryAppender(int maxElements) {
        queue = Queues.synchronizedQueue(EvictingQueue.create(maxElements));
    }


    public void reset() {
        queue.clear();
    }

    public Iterable<E> getEventsAndReset() {
        List<E> list = new ArrayList<>();
        E element = queue.poll();
        while (element != null) {
            list.add(element);
            element = queue.poll();
        }
        return list;
    }

    @Override
    protected void append(E eventObject) {
        queue.offer(eventObject);
    }
}
