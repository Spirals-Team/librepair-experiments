package org.zalando.riptide.failsafe;

import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.RetryPolicy;

public interface ConfigurationStage {
    UsageStage withRetryPolicy(RetryPolicy retryPolicy);
    UsageStage withCircuitBreaker(CircuitBreaker circuitBreaker);
}
