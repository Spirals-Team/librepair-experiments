package lv.robertsv.webjob;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.Scheduler;

import lv.robertsv.webjob.dto.Job;
import lv.robertsv.webjob.service.ScheduleManager;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleManagerTest {

	@Mock
	private Scheduler scheduler;

	@InjectMocks
	private ScheduleManager scheduleManager = new ScheduleManager();

	@Test
	public void verifyThatAddToScheduleSucceeds() throws Exception {
		scheduleManager.addToSchedule(new Job(1L, "\run.sh", "*/5 * * * *"));
		
		verify(scheduler, times(1)).start();
		verify(scheduler, times(1)).scheduleJob(anyObject(), anyObject());
	}

}