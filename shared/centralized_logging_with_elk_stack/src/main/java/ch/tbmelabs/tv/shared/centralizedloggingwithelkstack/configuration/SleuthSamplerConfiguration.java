package ch.tbmelabs.tv.shared.centralizedloggingwithelkstack.configuration;

import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SleuthSamplerConfiguration {
  @Bean
  public AlwaysSampler defaultSampler() {
    return new AlwaysSampler();
  }
}