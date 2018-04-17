package lv.robertsv.webjob;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
public class WebJobsSchedulerConfig {

	@Bean
	public StdSchedulerFactory quartzSchedulerFactoryBean() {
		return new StdSchedulerFactory();
	}

	@Bean
	public Scheduler quartzSchedulerBean(StdSchedulerFactory factory) throws SchedulerException {
		return factory.getScheduler();
	}

}
