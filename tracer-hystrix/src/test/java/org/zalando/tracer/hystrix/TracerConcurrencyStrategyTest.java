package org.zalando.tracer.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.zalando.tracer.Trace;
import org.zalando.tracer.Tracer;

import java.time.Duration;
import java.util.concurrent.Callable;

import static com.netflix.hystrix.exception.HystrixRuntimeException.FailureType.TIMEOUT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

@ExtendWith(HystrixExtension.class)
final class TracerConcurrencyStrategyTest {

    private final Tracer tracer = Tracer.builder()
            .trace("X-Trace", () -> "76f6046c-1b56-11e6-8c85-8fc9ee29f631")
            .build();
    private final Trace trace = tracer.get("X-Trace");

    @BeforeEach
    void setUp() {
        final HystrixPlugins plugins = HystrixPlugins.getInstance();
        final HystrixConcurrencyStrategy delegate = new HystrixConcurrencyStrategy() {
            @Override
            public <T> Callable<T> wrapCallable(final Callable<T> callable) {
                return () -> {
                    // to verify that the delegate has already access to the trace
                    assertThat(trace.getValue(), is("76f6046c-1b56-11e6-8c85-8fc9ee29f631"));
                    return callable.call();
                };
            }
        };
        plugins.registerConcurrencyStrategy(new TracerConcurrencyStrategy(tracer, delegate));
    }

    @Test
    void shouldGetTrace() {
        tracer.start();

        try {
            final String traceId = new GetTrace().execute();
            assertThat(traceId, is("76f6046c-1b56-11e6-8c85-8fc9ee29f631"));
        } finally {
            tracer.stop();
        }
    }

    @Test
    void shouldTimeOut() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            final HystrixRuntimeException exception = assertThrows(HystrixRuntimeException.class, () -> {
                tracer.start();
                try {
                    new TimesOut().execute();
                } finally {
                    tracer.stop();
                }
            });
            assertThat(exception.getFailureType(), is(TIMEOUT));
        });
    }

    private final class GetTrace extends HystrixCommand<String> {

        GetTrace() {
            super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        }

        @Override
        protected String run() {
            return trace.getValue();
        }

    }

    private final class TimesOut extends HystrixCommand<String> {

        TimesOut() {
            super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        }

        @Override
        protected String run() throws InterruptedException {
            Thread.sleep(10000);
            return trace.getValue();
        }

    }

}
