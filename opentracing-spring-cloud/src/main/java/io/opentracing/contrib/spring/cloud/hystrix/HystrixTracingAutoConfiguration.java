/**
 * Copyright 2017 The OpenTracing Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.opentracing.contrib.spring.cloud.hystrix;

import com.netflix.hystrix.HystrixCommand;
import feign.opentracing.hystrix.TracingConcurrencyStrategy;
import io.opentracing.Tracer;
import io.opentracing.contrib.spring.web.autoconfig.TracerAutoConfiguration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-configuration that {@link TracingConcurrencyStrategy} to Hystrix plugins.
 *
 * @author kameshsampath
 */
@Configuration
@ConditionalOnClass(HystrixCommand.class)
@ConditionalOnBean(Tracer.class)
@AutoConfigureAfter(TracerAutoConfiguration.class)
@ConditionalOnProperty(name = "opentracing.spring.cloud.hystrix.strategy.enabled", matchIfMissing = true)
public class HystrixTracingAutoConfiguration {

  @Bean
  TracingConcurrencyStrategy hystrixTracingConcurrencyStrategy(Tracer tracer) {
    return TracingConcurrencyStrategy.register(tracer);
  }
}
