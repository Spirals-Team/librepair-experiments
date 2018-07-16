package org.zalando.riptide.failsafe;

import com.google.common.annotations.VisibleForTesting;
import lombok.AllArgsConstructor;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.ExecutionContext;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.Listeners;
import net.jodah.failsafe.RetryPolicy;
import net.jodah.failsafe.SyncFailsafe;
import org.apiguardian.api.API;
import org.springframework.http.client.ClientHttpResponse;
import org.zalando.riptide.ConditionalIdempotentMethodDetector;
import org.zalando.riptide.DefaultIdempotentMethodDetector;
import org.zalando.riptide.DefaultSafeMethodDetector;
import org.zalando.riptide.IdempotencyKeyIdempotentMethodDetector;
import org.zalando.riptide.MethodDetector;
import org.zalando.riptide.OverrideSafeMethodDetector;
import org.zalando.riptide.Plugin;
import org.zalando.riptide.RequestArguments;
import org.zalando.riptide.RequestExecution;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;
import static org.apiguardian.api.API.Status.MAINTAINED;
import static org.zalando.riptide.CancelableCompletableFuture.forwardTo;
import static org.zalando.riptide.CancelableCompletableFuture.preserveCancelability;

@API(status = MAINTAINED)
@AllArgsConstructor
public final class FailsafePlugin {

    public interface ConfigurableStage extends Plugin {
        ConfigurableStage withRetryPolicy(RetryPolicy retryPolicy);
        ConfigurableStage withCircuitBreaker(CircuitBreaker circuitBreaker);
        ConfigurableStage withIdempotentMethodDetector(MethodDetector detector);
        ConfigurableStage withListener(final RetryListener listener);
    }

    private final ScheduledExecutorService scheduler;

    public ConfigurableStage withRetryPolicy(final RetryPolicy retryPolicy) {
        return new Stage(scheduler, retryPolicy, null);
    }

    public ConfigurableStage withCircuitBreaker(final CircuitBreaker circuitBreaker) {
        return new Stage(scheduler, null, circuitBreaker);
    }

    @AllArgsConstructor(access = PRIVATE)
    private static final class Stage implements ConfigurableStage {

        private final ScheduledExecutorService scheduler;
        private final MethodDetector idempotent;
        private final RetryPolicy retryPolicy;
        private final CircuitBreaker circuitBreaker;
        private final RetryListener listener;


        private Stage(final ScheduledExecutorService scheduler,
                @Nullable final RetryPolicy retryPolicy, @Nullable final CircuitBreaker circuitBreaker) {
            this(scheduler, MethodDetector.compound(
                    new DefaultIdempotentMethodDetector(MethodDetector.compound(
                            new DefaultSafeMethodDetector(),
                            new OverrideSafeMethodDetector()
                    )),
                    new ConditionalIdempotentMethodDetector(),
                    new IdempotencyKeyIdempotentMethodDetector()
            ), retryPolicy, circuitBreaker, RetryListener.DEFAULT);
        }

        @Override
        public ConfigurableStage withIdempotentMethodDetector(final MethodDetector idempotent) {
            return new Stage(scheduler, idempotent, retryPolicy, circuitBreaker, listener);
        }

        @Override
        public ConfigurableStage withRetryPolicy(final RetryPolicy retryPolicy) {
            return new Stage(scheduler, idempotent, retryPolicy, circuitBreaker, listener);
        }

        @Override
        public ConfigurableStage withCircuitBreaker(final CircuitBreaker circuitBreaker) {
            return new Stage(scheduler, idempotent, retryPolicy, circuitBreaker, listener);
        }

        @Override
        public ConfigurableStage withListener(final RetryListener listener) {
            return new Stage(scheduler, idempotent, retryPolicy, circuitBreaker, listener);
        }

        @Override
        public RequestExecution beforeDispatch(final RequestExecution execution) {
            return arguments -> {
                final SyncFailsafe<Object> failsafe = select(retryPolicy, circuitBreaker, arguments);

                final CompletableFuture<ClientHttpResponse> original = failsafe
                        .with(scheduler)
                        .with(new RetryListenersAdapter(listener, arguments))
                        .future(() -> execution.execute(arguments));

                final CompletableFuture<ClientHttpResponse> cancelable = preserveCancelability(original);
                original.whenComplete(forwardTo(cancelable));
                return cancelable;
            };
        }

        private SyncFailsafe<Object> select(@Nullable final RetryPolicy retryPolicy,
                @Nullable final CircuitBreaker circuitBreaker, final RequestArguments arguments) {

            if (retryPolicy != null && !idempotent.test(arguments)) {
                return select(null, circuitBreaker, arguments);
            }

            if (retryPolicy == null) {
                return Failsafe.with(requireNonNull(circuitBreaker));
            } else if (circuitBreaker == null) {
                return Failsafe.with(retryPolicy);
            } else {
                return Failsafe.with(retryPolicy).with(circuitBreaker);
            }
        }

    }

    @VisibleForTesting
    static final class RetryListenersAdapter extends Listeners<ClientHttpResponse> {

        private final RequestArguments arguments;
        private RetryListener listener;

        public RetryListenersAdapter(final RetryListener listener, final RequestArguments arguments) {
            this.arguments = arguments;
            this.listener = listener;
        }

        @Override
        public void onRetry(final ClientHttpResponse result, final Throwable failure,
                final ExecutionContext context) {
            listener.onRetry(arguments, result, failure, context);
        }

    }

}
