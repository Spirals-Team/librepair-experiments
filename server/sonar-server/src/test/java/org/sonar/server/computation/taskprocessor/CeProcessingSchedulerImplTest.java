/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.computation.taskprocessor;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableScheduledFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.sonar.server.computation.configuration.CeConfigurationRule;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CeProcessingSchedulerImplTest {
  private static final Error ERROR_TO_INTERRUPT_CHAINING = new Error("Error should stop scheduling");

  @Rule
  // due to risks of infinite chaining of tasks/futures, a timeout is required for safety
  public Timeout timeout = Timeout.seconds(60);
  @Rule
  public CeConfigurationRule ceConfiguration = new CeConfigurationRule();

  private CeWorkerCallable ceWorkerRunnable = mock(CeWorkerCallable.class);
  private StubCeProcessingSchedulerExecutorService processingExecutorService = new StubCeProcessingSchedulerExecutorService();
  private SchedulerCall regularDelayedPoll = new SchedulerCall(ceWorkerRunnable, 2000L, TimeUnit.MILLISECONDS);
  private SchedulerCall notDelayedPoll = new SchedulerCall(ceWorkerRunnable);

  private CeProcessingSchedulerImpl underTest = new CeProcessingSchedulerImpl(ceConfiguration, processingExecutorService, ceWorkerRunnable);

  @Test
  public void polls_without_delay_when_CeWorkerCallable_returns_true() throws Exception {
    when(ceWorkerRunnable.call())
      .thenReturn(true)
      .thenThrow(ERROR_TO_INTERRUPT_CHAINING);

    startSchedulingAndRun();

    assertThat(processingExecutorService.getSchedulerCalls()).containsOnly(
      regularDelayedPoll,
      notDelayedPoll
      );
  }

  @Test
  public void polls_without_delay_when_CeWorkerCallable_throws_Exception_but_not_Error() throws Exception {
    when(ceWorkerRunnable.call())
      .thenThrow(new Exception("Exception is followed by a poll without delay"))
      .thenThrow(ERROR_TO_INTERRUPT_CHAINING);

    startSchedulingAndRun();

    assertThat(processingExecutorService.getSchedulerCalls()).containsExactly(
      regularDelayedPoll,
      notDelayedPoll
      );
  }

  @Test
  public void polls_with_regular_delay_when_CeWorkerCallable_returns_false() throws Exception {
    when(ceWorkerRunnable.call())
      .thenReturn(false)
      .thenThrow(ERROR_TO_INTERRUPT_CHAINING);

    startSchedulingAndRun();

    assertThat(processingExecutorService.getSchedulerCalls()).containsExactly(
      regularDelayedPoll,
      regularDelayedPoll
      );
  }

  @Test
  public void startScheduling_schedules_CeWorkerCallable_at_fixed_rate_run_head_of_queue() throws Exception {
    when(ceWorkerRunnable.call())
      .thenReturn(true)
      .thenReturn(true)
      .thenReturn(false)
      .thenReturn(true)
      .thenReturn(false)
      .thenThrow(new Exception("IAE should not cause scheduling to stop"))
      .thenReturn(false)
      .thenReturn(false)
      .thenReturn(false)
      .thenThrow(ERROR_TO_INTERRUPT_CHAINING);

    startSchedulingAndRun();

    assertThat(processingExecutorService.getSchedulerCalls()).containsExactly(
      regularDelayedPoll,
      notDelayedPoll,
      notDelayedPoll,
      regularDelayedPoll,
      notDelayedPoll,
      regularDelayedPoll,
      notDelayedPoll,
      regularDelayedPoll,
      regularDelayedPoll,
      regularDelayedPoll
      );
  }

  @Test
  public void stop_cancels_next_polling_and_does_not_add_any_new_one() throws Exception {
    when(ceWorkerRunnable.call())
      .thenReturn(false)
      .thenReturn(true)
      .thenReturn(false)
      .thenReturn(false)
      .thenReturn(false)
      .thenReturn(false)
      .thenReturn(false)
      .thenThrow(ERROR_TO_INTERRUPT_CHAINING);

    underTest.startScheduling();

    int cancelledTaskFutureCount = 0;
    int i = 0;
    while (processingExecutorService.futures.peek() != null) {
      Future<?> future = processingExecutorService.futures.poll();
      if (future.isCancelled()) {
        cancelledTaskFutureCount++;
      } else {
        future.get();
      }
      // call stop after second delayed polling
      if (i == 1) {
        underTest.stop();
      }
      i++;
    }

    assertThat(cancelledTaskFutureCount).isEqualTo(1);
    assertThat(processingExecutorService.getSchedulerCalls()).containsExactly(
      regularDelayedPoll,
      regularDelayedPoll,
      notDelayedPoll,
      regularDelayedPoll
      );
  }

  @Test
  public void when_workerCount_is_more_than_1_as_many_CeWorkerCallable_are_scheduled() throws InterruptedException {
    int workerCount = Math.abs(new Random().nextInt(10)) + 1;

    ceConfiguration.setWorkerCount(workerCount);

    ListenableScheduledFuture listenableScheduledFuture = mock(ListenableScheduledFuture.class);
    CeProcessingSchedulerExecutorService processingExecutorService = mock(CeProcessingSchedulerExecutorService.class);
    CeProcessingSchedulerImpl underTest = new CeProcessingSchedulerImpl(ceConfiguration, processingExecutorService, ceWorkerRunnable);
    when(processingExecutorService.schedule(ceWorkerRunnable, ceConfiguration.getQueuePollingDelay(), MILLISECONDS))
        .thenReturn(listenableScheduledFuture);

    underTest.startScheduling();

    verify(processingExecutorService, times(workerCount)).schedule(ceWorkerRunnable, ceConfiguration.getQueuePollingDelay(), MILLISECONDS);
    verify(listenableScheduledFuture, times(workerCount)).addListener(any(Runnable.class), eq(processingExecutorService));
  }

  private void startSchedulingAndRun() throws ExecutionException, InterruptedException {
    underTest.startScheduling();

    // execute future synchronously
    processingExecutorService.runFutures();
  }

  /**
   * A synchronous implementation of {@link CeProcessingSchedulerExecutorService} which exposes a synchronous
   * method to execute futures it creates and exposes a method to retrieve logs of calls to
   * {@link CeProcessingSchedulerExecutorService#schedule(Callable, long, TimeUnit)} which is used by
   * {@link CeProcessingSchedulerImpl}.
   */
  private static class StubCeProcessingSchedulerExecutorService implements CeProcessingSchedulerExecutorService {

    private final Queue<Future<?>> futures = new ConcurrentLinkedQueue<>();
    private final ListeningScheduledExecutorService delegate = MoreExecutors.listeningDecorator(new SynchronousStubExecutorService());

    private final List<SchedulerCall> schedulerCalls = new ArrayList<>();

    public List<SchedulerCall> getSchedulerCalls() {
      return schedulerCalls;
    }

    public void runFutures() throws ExecutionException, InterruptedException {
      while (futures.peek() != null) {
        Future<?> future = futures.poll();
        if (!future.isCancelled()) {
          future.get();
        }
      }
    }

    @Override
    public <V> ListenableScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
      this.schedulerCalls.add(new SchedulerCall(callable, delay, unit));
      return delegate.schedule(callable, delay, unit);
    }

    @Override
    public <T> ListenableFuture<T> submit(Callable<T> task) {
      this.schedulerCalls.add(new SchedulerCall(task));
      return delegate.submit(task);
    }

    @Override
    public void stop() {
      throw new UnsupportedOperationException("stop() not implemented");
    }

    // ////////////// delegated methods ////////////////

    @Override
    public ListenableScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
      return delegate.schedule(command, delay, unit);
    }

    @Override
    public ListenableScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
      return delegate.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    @Override
    public ListenableScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
      return delegate.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    @Override
    public void shutdown() {
      delegate.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
      return delegate.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
      return delegate.isShutdown();
    }

    @Override
    public boolean isTerminated() {
      return delegate.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      return delegate.awaitTermination(timeout, unit);
    }

    @Override
    public <T> ListenableFuture<T> submit(Runnable task, T result) {
      return delegate.submit(task, result);
    }

    @Override
    public ListenableFuture<?> submit(Runnable task) {
      return delegate.submit(task);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
      return delegate.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
      return delegate.invokeAll(tasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
      return delegate.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      return delegate.invokeAny(tasks, timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
      delegate.execute(command);
    }

    /**
     * A partial (only 3 methods) implementation of ScheduledExecutorService which stores futures it creates into
     * {@link StubCeProcessingSchedulerExecutorService#futures}.
     */
    private class SynchronousStubExecutorService implements ScheduledExecutorService {
      @Override
      public ScheduledFuture<?> schedule(final Runnable command, long delay, TimeUnit unit) {
        ScheduledFuture<Void> res = new AbstractPartiallyImplementedScheduledFuture<Void>() {
          @Override
          public Void get() throws InterruptedException, ExecutionException {
            command.run();
            return null;
          }
        };
        futures.add(res);
        return res;
      }

      @Override
      public <V> ScheduledFuture<V> schedule(final Callable<V> callable, long delay, TimeUnit unit) {
        ScheduledFuture<V> res = new AbstractPartiallyImplementedScheduledFuture<V>() {

          @Override
          public V get() throws InterruptedException, ExecutionException {
            try {
              return callable.call();
            } catch (Exception e) {
              throw new ExecutionException(e);
            }
          }
        };
        futures.add(res);
        return res;
      }

      @Override
      public <T> Future<T> submit(final Callable<T> task) {
        Future<T> res = new AbstractPartiallyImplementedFuture<T>() {

          @Override
          public T get() throws InterruptedException, ExecutionException {
            try {
              return task.call();
            } catch (Exception e) {
              throw new ExecutionException(e);
            }
          }

        };
        futures.add(res);
        return res;
      }

      @Override
      public void execute(Runnable command) {
        command.run();
      }

      // ///////// unsupported operations ///////////

      @Override
      public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        throw new UnsupportedOperationException("scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) not implemented");
      }

      @Override
      public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        throw new UnsupportedOperationException("scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) not implemented");
      }

      @Override
      public void shutdown() {
        throw new UnsupportedOperationException("shutdown() not implemented");
      }

      @Override
      public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException("shutdownNow() not implemented");
      }

      @Override
      public boolean isShutdown() {
        throw new UnsupportedOperationException("isShutdown() not implemented");
      }

      @Override
      public boolean isTerminated() {
        throw new UnsupportedOperationException("isTerminated() not implemented");
      }

      @Override
      public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException("awaitTermination(long timeout, TimeUnit unit) not implemented");
      }

      @Override
      public <T> Future<T> submit(Runnable task, T result) {
        throw new UnsupportedOperationException("submit(Runnable task, T result) not implemented");
      }

      @Override
      public Future<?> submit(Runnable task) {
        throw new UnsupportedOperationException("submit(Runnable task) not implemented");
      }

      @Override
      public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        throw new UnsupportedOperationException("invokeAll(Collection<? extends Callable<T>> tasks) not implemented");
      }

      @Override
      public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException("invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) not implemented");
      }

      @Override
      public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        throw new UnsupportedOperationException("invokeAny(Collection<? extends Callable<T>> tasks) not implemented");
      }

      @Override
      public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        throw new UnsupportedOperationException("invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) not implemented");
      }
    }
  }

  private static abstract class AbstractPartiallyImplementedScheduledFuture<V> extends AbstractPartiallyImplementedFuture<V> implements ScheduledFuture<V> {
    @Override
    public long getDelay(TimeUnit unit) {
      throw new UnsupportedOperationException("getDelay(TimeUnit unit) not implemented");
    }

    @Override
    public int compareTo(Delayed o) {
      throw new UnsupportedOperationException("compareTo(Delayed o) not implemented");
    }

  }

  private static abstract class AbstractPartiallyImplementedFuture<T> implements Future<T> {
    private boolean cancelled = false;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
      this.cancelled = true;
      return true;
    }

    @Override
    public boolean isCancelled() {
      return this.cancelled;
    }

    @Override
    public boolean isDone() {
      throw new UnsupportedOperationException("isDone() not implemented");
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      throw new UnsupportedOperationException("get(long timeout, TimeUnit unit) not implemented");
    }
  }

  /**
   * Used to log parameters of calls to {@link CeProcessingSchedulerExecutorService#schedule(Callable, long, TimeUnit)}
   */
  @Immutable
  private static final class SchedulerCall {
    private final Callable<?> callable;
    private final long delay;
    private final TimeUnit unit;

    private SchedulerCall(Callable<?> callable, long delay, TimeUnit unit) {
      this.callable = callable;
      this.delay = delay;
      this.unit = unit;
    }

    private SchedulerCall(Callable<?> callable) {
      this.callable = callable;
      this.delay = -63366;
      this.unit = TimeUnit.NANOSECONDS;
    }

    @Override
    public boolean equals(@Nullable Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      SchedulerCall that = (SchedulerCall) o;
      return delay == that.delay && callable == that.callable && unit.equals(that.unit);
    }

    @Override
    public int hashCode() {
      return Objects.hash(callable, delay, unit);
    }

    @Override
    public String toString() {
      return "SchedulerCall{" +
        "callable=" + callable +
        ", delay=" + delay +
        ", unit=" + unit +
        '}';
    }
  }

}
