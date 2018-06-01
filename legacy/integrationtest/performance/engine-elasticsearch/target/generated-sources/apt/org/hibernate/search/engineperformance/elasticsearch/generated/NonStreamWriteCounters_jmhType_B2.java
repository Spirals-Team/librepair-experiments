package org.hibernate.search.engineperformance.elasticsearch.generated;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
public class NonStreamWriteCounters_jmhType_B2 extends NonStreamWriteCounters_jmhType_B1 {
    public volatile int setupTrialMutex;
    public volatile int tearTrialMutex;
    public final static AtomicIntegerFieldUpdater<NonStreamWriteCounters_jmhType_B2> setupTrialMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(NonStreamWriteCounters_jmhType_B2.class, "setupTrialMutex");
    public final static AtomicIntegerFieldUpdater<NonStreamWriteCounters_jmhType_B2> tearTrialMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(NonStreamWriteCounters_jmhType_B2.class, "tearTrialMutex");

    public volatile int setupIterationMutex;
    public volatile int tearIterationMutex;
    public final static AtomicIntegerFieldUpdater<NonStreamWriteCounters_jmhType_B2> setupIterationMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(NonStreamWriteCounters_jmhType_B2.class, "setupIterationMutex");
    public final static AtomicIntegerFieldUpdater<NonStreamWriteCounters_jmhType_B2> tearIterationMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(NonStreamWriteCounters_jmhType_B2.class, "tearIterationMutex");

    public volatile int setupInvocationMutex;
    public volatile int tearInvocationMutex;
    public final static AtomicIntegerFieldUpdater<NonStreamWriteCounters_jmhType_B2> setupInvocationMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(NonStreamWriteCounters_jmhType_B2.class, "setupInvocationMutex");
    public final static AtomicIntegerFieldUpdater<NonStreamWriteCounters_jmhType_B2> tearInvocationMutexUpdater = AtomicIntegerFieldUpdater.newUpdater(NonStreamWriteCounters_jmhType_B2.class, "tearInvocationMutex");

}
