package ru.job4j.last.main;

import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.last.config.Config;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Yury Matskevich
 */
public class ScheduleRun {
	private static final Logger LOG = Logger.getLogger(ScheduleRun.class);
	private String str;
	private String cronTime;

	public ScheduleRun(String config) {
		str = config;
		cronTime = new Config(config).getProperty("cron.time");
		start();
	}

	private void start() {
		try {
			JobDetail job = newJob(MyJob.class)
					.withIdentity("job1", "group1")
					.build();
			CronTrigger trigger = newTrigger()
					.withIdentity("trigger1", "group1")
					.withSchedule(cronSchedule(cronTime))
					.build();
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();
			sched.getContext().put("name", str);
			sched.start();
			sched.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
