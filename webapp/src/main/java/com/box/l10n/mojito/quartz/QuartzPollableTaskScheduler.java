package com.box.l10n.mojito.quartz;

import com.box.l10n.mojito.entity.PollableTask;
import com.box.l10n.mojito.json.ObjectMapper;
import com.box.l10n.mojito.service.pollableTask.PollableFuture;
import com.box.l10n.mojito.service.pollableTask.PollableTaskService;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.box.l10n.mojito.quartz.QuartzConfig.DYNAMIC_GROUP_NAME;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Component
public class QuartzPollableTaskScheduler {

    @Autowired
    Scheduler scheduler;

    @Autowired
    PollableTaskService pollableTaskService;

    @Autowired
    ObjectMapper objectMapper;

    public <T> PollableFuture<T> scheduleJob(Class clazz, Object input) {

        String pollableTaskName = clazz.getCanonicalName();

        final PollableTask pollableTask = pollableTaskService.createPollableTask(null, pollableTaskName, null, 0);

        String keyName = pollableTaskName + '_' + pollableTask.getId();

        JobDetail jobDetail = JobBuilder.newJob().ofType(clazz)
                .withIdentity(keyName, DYNAMIC_GROUP_NAME)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .startNow()
                .withSchedule(simpleSchedule().withMisfireHandlingInstructionFireNow())
                .forJob(jobDetail)
                .usingJobData(QuartzPollableJob.POLLABLE_TASK_ID, pollableTask.getId().toString())
                .usingJobData(QuartzPollableJob.INPUT, objectMapper.writeValueAsStringUnsafe(input))
                .withIdentity(keyName, DYNAMIC_GROUP_NAME).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        return new QuartzPollableFutureTask<T>(pollableTask);
    }

}
