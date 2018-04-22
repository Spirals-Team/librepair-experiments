package model;


import model.interfaces.IRentalState;
import model.jobs.VerifyTenantConfirmationJob;
import model.states.rental.PendingRentalST;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Rental {

    private Reservation reservation;
    private IRentalState state;
    private LocalDateTime beginRentalTime = null;
    public static int JOBCOUNT = 0;

    public Rental(Reservation reservation){

        this.reservation = reservation;
        this.state = new PendingRentalST();

    }

    public IRentalState getState(){
        return this.state;
    }

    public void ownerConfirmation(){

        this.state.ownerUserConfirmated(this);

        this.throwJob(VerifyTenantConfirmationJob.class);

    }

    private void throwJob(Class<? extends Job> verify){
        JOBCOUNT++;
        try {

            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();

            JobDetail jobDetail = newJob(verify)
                    .withIdentity("job"+JOBCOUNT, Scheduler.DEFAULT_GROUP)
                    .build();

            jobDetail.getJobDataMap().put(VerifyTenantConfirmationJob.RENTAL, this);

            Trigger trigger = newTrigger()
                    .withIdentity("trigger", Scheduler.DEFAULT_GROUP)
                    .startNow()
                    .withSchedule(simpleSchedule())
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();

            // passing true into the shutdown message tells the Quartz Scheduler to wait until all jobs
            // have completed running before returning from the method call.
            //scheduler.shutdown(true);

            //scheduler.deleteJob(JobKey.jobKey("job"+this.jobCount));

        }catch (SchedulerException exception){
            exception.printStackTrace();
        }
    }

    public void tenantConfirmation(){
        this.state.tenantUserConfirmated(this);
        //this.throwJob();
    }

    public void setState(IRentalState newState){
        this.state=newState;
    }

    public void startRentalTime(){
        this.beginRentalTime = LocalDateTime.now();
    }

    public long getRentalTime(){
        return this.beginRentalTime.until(LocalDateTime.now(), ChronoUnit.DAYS);
    }

    public User getTenantUser(){
        return this.reservation.getTenantUser();
    }

    public User getOwnerUser(){
        return this.reservation.getOwnerUser();
    }

    public double rentCost(LocalDateTime endRentalTime) {
        long days= this.beginRentalTime.until(endRentalTime, ChronoUnit.DAYS);
        return this.reservation.getPost().getCostPerDay()*days;
    }

    public void tenantUserConfirmatedReturn(int score, String comment) {
        this.getState().tenantUserConfirmated(this, score, comment);
    }

    public void ownerUserConfirmatedReturn(int score, String comment) {
        this.getState().ownerUserConfirmated(this, score, comment);
    }

    public LocalDateTime getBeginRentalTime(){
        return this.beginRentalTime;
    }
}
