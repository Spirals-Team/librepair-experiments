package com.box.l10n.mojito.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class QuartzSchedulerConfig {

    /**
     * logger
     */
    static Logger logger = LoggerFactory.getLogger(QuartzSchedulerConfig.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    DataSource dataSource;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    QuartzPropertiesConfig quartzPropertiesConfig;

    @Autowired(required = false)
    List<Trigger> triggers = new ArrayList<>();

    /**
     * Creates the scheduler with triggers/jobs defined in spring beans.
     * <p>
     * The spring beans should use the default group so that it is easy to keep track of new or removed triggers/jobs.
     * <p>
     * In {@link #startScheduler()} triggers/jobs present in Quartz but without a matching spring bean will be
     * removed.
     * <p>
     * Other job and trigger created dynamically must not used the default group else they'll be removed.
     *
     * @return
     * @throws SchedulerException
     */
    @Bean
    public SchedulerFactoryBean scheduler() throws SchedulerException {

        Properties quartzProperties = quartzPropertiesConfig.getQuartzProperties();

        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();

        String dataSource = quartzProperties.getProperty("org.quartz.jobStore.dataSource");

        if (dataSource == null) {
            logger.info("Use spring data source for Quartz");
            schedulerFactory.setDataSource(this.dataSource);
            schedulerFactory.setTransactionManager(transactionManager);
        } else {
            logger.info("Use Quartz settings to configure the datasource");
        }
        schedulerFactory.setQuartzProperties(quartzProperties);
        schedulerFactory.setJobFactory(springBeanJobFactory());

        schedulerFactory.setGlobalJobListeners(new JobListener() {
            @Override
            public String getName() {
                return "MojitoJobListener";
            }

            @Override
            public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
                logger.info("jobToBeExecuted: {}", jobExecutionContext.getTrigger().getKey());
            }

            @Override
            public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
                logger.info("jobExecutionVetoed: {}", jobExecutionContext.getTrigger().getKey());
            }

            @Override
            public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
                logger.info("jobWasExecuted: {}", jobExecutionContext.getTrigger().getKey());
            }
        });

        schedulerFactory.setSchedulerListeners(new SchedulerListener() {
            @Override
            public void jobScheduled(Trigger trigger) {
                logger.info("jobScheduled: {}", trigger.getKey());
            }

            @Override
            public void jobUnscheduled(TriggerKey triggerKey) {
                logger.info("jobUnscheduled: {}", triggerKey);
            }

            @Override
            public void triggerFinalized(Trigger trigger) {
                logger.info("triggerFinalized: {}", trigger.getKey());
            }

            @Override
            public void triggerPaused(TriggerKey triggerKey) {
                logger.info("triggerPaused: {}", triggerKey);
            }

            @Override
            public void triggersPaused(String s) {

                logger.info("triggersPaused: {}", s);
            }

            @Override
            public void triggerResumed(TriggerKey triggerKey) {

                logger.info("triggerResumed: {}", triggerKey);
            }

            @Override
            public void triggersResumed(String s) {
                logger.info("triggersResumed: {}", s);
            }

            @Override
            public void jobAdded(JobDetail jobDetail) {
                logger.info("jobAdded: {}", jobDetail.getKey());
            }

            @Override
            public void jobDeleted(JobKey jobKey) {
                logger.info("jobDeleted: {}", jobKey);
            }

            @Override
            public void jobPaused(JobKey jobKey) {
                logger.info("jobPaused: {}", jobKey);

            }

            @Override
            public void jobsPaused(String s) {
                logger.info("jobsPaused: {}", s);

            }

            @Override
            public void jobResumed(JobKey jobKey) {
                logger.info("jobResumed: {}", jobKey);

            }

            @Override
            public void jobsResumed(String s) {

                logger.info("jobsResumed: {}", s);
            }

            @Override
            public void schedulerError(String s, SchedulerException e) {
                logger.error("schedulerError - s: {}", s);
                logger.error("schedulerError: {}", e);
            }

            @Override
            public void schedulerInStandbyMode() {
                logger.info("schedulerInStandbyMode");

            }

            @Override
            public void schedulerStarted() {
                logger.info("schedulerStarted");

            }

            @Override
            public void schedulerStarting() {
                logger.info("schedulerStarting");

            }

            @Override
            public void schedulerShutdown() {
                logger.info("schedulerShutdown");

            }

            @Override
            public void schedulerShuttingdown() {
                logger.info("schedulerShuttingdown");

            }

            @Override
            public void schedulingDataCleared() {
                logger.info("schedulingDataCleared");

            }
        });


        schedulerFactory.setGlobalTriggerListeners(new TriggerListener() {
            @Override
            public String getName() {
                return "mojitotriggerlistener";
            }

            @Override
            public void triggerFired(Trigger trigger, JobExecutionContext context) {
                logger.info("triggerFired: {}", trigger.getKey());
            }

            @Override
            public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
                logger.info("vetoJobExecution: {}", trigger.getKey());
                return false;
            }

            @Override
            public void triggerMisfired(Trigger trigger) {

                logger.info("triggerMisfired: {}", trigger.getKey());
            }

            @Override
            public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {

                logger.info("triggerComplete: {}", trigger.getKey());

            }
        });


        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setTriggers(triggers.toArray(new Trigger[]{}));
        schedulerFactory.setAutoStartup(false);

        return schedulerFactory;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }
}