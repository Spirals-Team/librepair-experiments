package org.corfudb.test.concurrent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.IntConsumer;
import javax.annotation.Nonnull;
import org.corfudb.runtime.exceptions.unrecoverable.UnrecoverableCorfuInterruptedError;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ConcurrentScheduler {

    final ExtensionContext extensionContext;
    final List<Callable<Void>> operations;

    public ConcurrentScheduler(@Nonnull ExtensionContext executionContext) {
        this.extensionContext = executionContext;
        this.operations = new ArrayList<>();
    }

    public void schedule(int iterations, @Nonnull IntConsumer operation) {
        for (int i = 0; i < iterations; i++) {
            final int iteration = i;
            operations.add(() -> { operation.accept(iteration); return null; });
        }
    }

    public void execute(int concurrency, @Nonnull Duration timeout) {
        ExecutorService service = Executors.newFixedThreadPool(concurrency,
            new ThreadFactoryBuilder()
                    .setDaemon(true)
                    .setNameFormat("test-%d")
                    .build());
        try {
            List<Future<Void>> futures =
                service.invokeAll(operations, timeout.toMillis(), TimeUnit.MILLISECONDS);
            for (Future<Void> future : futures) {
                future.get();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new UnrecoverableCorfuInterruptedError(e);
        } catch (ExecutionException e) {
            throw (RuntimeException) e.getCause();
        } finally {
            service.shutdownNow();
            operations.clear();
        }
    }
}
