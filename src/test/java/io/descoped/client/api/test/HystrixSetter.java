package io.descoped.client.api.test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

public class HystrixSetter {

    public static HystrixCommand.Setter setterFallback() {
        return HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("OperationCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(300000))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withMaxQueueSize(100)
                                .withQueueSizeRejectionThreshold(100)
                                .withCoreSize(4));
    }

    public static HystrixCommand.Setter setterCircuitBreaker() {
        return HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("OperationCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                    .withCircuitBreakerEnabled(true)
                    .withCircuitBreakerErrorThresholdPercentage(50)
                    .withCircuitBreakerRequestVolumeThreshold(20)
                    .withCircuitBreakerSleepWindowInMilliseconds(5_000)
                );
    }

}
