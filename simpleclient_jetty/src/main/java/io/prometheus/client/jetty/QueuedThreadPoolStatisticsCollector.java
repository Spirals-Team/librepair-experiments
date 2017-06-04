package io.prometheus.client.jetty;

import io.prometheus.client.Collector;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class QueuedThreadPoolStatisticsCollector extends Collector {

  private final QueuedThreadPool queuedThreadPool;

  public QueuedThreadPoolStatisticsCollector(QueuedThreadPool queuedThreadPool) {
    this.queuedThreadPool = queuedThreadPool;
  }

  @Override
  public List<MetricFamilySamples> collect() {
    return Arrays.asList(
        buildGauge("queued_thread_pool_threads", "Number of total threads",
            queuedThreadPool.getThreads()),
        buildGauge("queued_thread_pool_idle_threads", "Number of idle threads",
            queuedThreadPool.getIdleThreads()),
        buildGauge("queued_thread_pool_jobs", "Number of total jobs",
            queuedThreadPool.getQueueSize())
    );
  }

  private static MetricFamilySamples buildGauge(String name, String help, double value) {
    return new MetricFamilySamples(
        name,
        Type.GAUGE,
        help,
        Collections
            .singletonList(new MetricFamilySamples.Sample(name,
                Collections.<String>emptyList(), Collections.<String>emptyList(), value)));
  }
}
