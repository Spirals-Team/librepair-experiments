package lv.robertsv.webjob;

import org.dozer.DozerBeanMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@ComponentScan
@Import({WebJobsWebSocketConfig.class, WebJobsJpaConfig.class, WebJobsSchedulerConfig.class})
public class WebJobsApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebJobsApplication.class, args);
	}

	@Bean
	public DozerBeanMapper dozerMapperBean() {
		DozerBeanMapper dozerBean = new DozerBeanMapper();
		return dozerBean;
	}
	
}
