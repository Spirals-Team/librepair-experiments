package ru.job4j.task.examples;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

//https://ru.stackoverflow.com/questions/585061/%D0%97%D0%B0%D0%BF%D1%83%D1%81%D0%BA-%D0%BF%D0%BE-%D0%B2%D1%80%D0%B5%D0%BC%D0%B5%D0%BD%D0%B8
public class QuartzJob implements Job {



    public static void main(String[] args) {
//        QuartzJob quartzJob = new QuartzJob();
//        quartzJob.execute();




        JobDetail job = JobBuilder
                .newJob(QuartzJob.class)
                .withIdentity("QuartzJob", "group1")
                .build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("QuartzTrigger", "group1")
                .withSchedule(CronScheduleBuilder
                        .cronSchedule("12 22 9-18/1 ? * MON-FRI"))
                .build();


        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler sched = null;
        try {
            sched = schedFact.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
            sched.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }


    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        //запускаемая по расписанию задача
        System.out.println("asdf");

    }
}