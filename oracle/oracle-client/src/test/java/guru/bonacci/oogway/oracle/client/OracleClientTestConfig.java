package guru.bonacci.oogway.oracle.client;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = { OracleClientConfig.class, 
												  OracleClientTests.App.class, 
												  OracleClientTests.LocalRibbonClientConfiguration.class }))
@EnableFeignClients(basePackageClasses = OracleClientCredentialsClient.class)
public class OracleClientTestConfig {
}
