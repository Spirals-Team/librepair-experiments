package io.prometheus.client.jetty;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import io.prometheus.client.CollectorRegistry;
import java.util.concurrent.BlockingQueue;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QueuedThreadPoolStatisticsCollectorTest {

  private Server server;
  private QueuedThreadPool queuedThreadPool;

  @Before
  public void setUp() {
    BlockingQueue<Runnable> queue = new BlockingArrayQueue<Runnable>(8, 1024, 1024);
    queuedThreadPool = new QueuedThreadPool(200, 8, 60000, queue);
    server =  new Server(queuedThreadPool);
  }

  @After
  public void tearDown() throws Exception {
    server.stop();
  }

  @Test
  public void metricsGathered() throws Exception {

    new QueuedThreadPoolStatisticsCollector(queuedThreadPool).register();
    server.start();

    assertNotEquals(0d,
        CollectorRegistry.defaultRegistry.getSampleValue("queued_thread_pool_threads",
            new String[]{}, new String[]{}).intValue());
    assertNotEquals(0d,
        CollectorRegistry.defaultRegistry.getSampleValue("queued_thread_pool_idle_threads",
            new String[]{}, new String[]{}));
    assertNotNull(CollectorRegistry.defaultRegistry.getSampleValue("queued_thread_pool_jobs",
        new String[]{}, new String[]{}));
  }
}
