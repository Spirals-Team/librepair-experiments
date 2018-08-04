package ru.job4j.last.main;

import org.apache.log4j.Logger;
import org.quartz.*;
import ru.job4j.last.connection.NoSuchDbException;

/**
 * @author Yury Matskevich
 */
public class MyJob implements Job {
	private static final Logger LOG = Logger.getLogger(MyJob.class);
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			SchedulerContext schedulerContext = jobExecutionContext
					.getScheduler()
					.getContext();
			String conf = (String) schedulerContext.get("name");
			new Parse(conf).addFoundOffersToStore();
		} catch (SchedulerException | NoSuchDbException e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
