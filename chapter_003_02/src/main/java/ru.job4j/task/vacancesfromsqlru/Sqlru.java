package ru.job4j.task.vacancesfromsqlru;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Sqlru implements Job {
    private static final Logger LOGGG = LoggerFactory.getLogger(Sqlru.class);
    Connection connection;

    /**
     * if you want jast start the program without scheduler
     * Sqlru sqlru = new Sqlru();
     * sqlru.executeProgram();
     */
    public static void main(String[] args) {
        schedulerEveryDayInAppropriateTimaRunTaskSqlru("18", "18");
    }

    private static void schedulerEveryDayInAppropriateTimaRunTaskSqlru(String hour, String minute) {
        JobDetail job = JobBuilder.newJob(Sqlru.class).withIdentity("Sqlru", "group1").build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("SqlruTrigger", "group1").withSchedule(CronScheduleBuilder.cronSchedule("0 " + minute + " " + hour + " ? * *")).build();
        SchedulerFactory schedFact = new StdSchedulerFactory();
        Scheduler sched = null;
        try {
            sched = schedFact.getScheduler();
            sched.start();
            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            LOGGG.error(e.getMessage(), e);
        }
    }

    private void executeProgram() {
        LOGGG.info("executeProgram() ");
        System.out.println("executeProgram() ");
        connection = this.getInitConnection();
        WorkWithDataBase workWithDataBase = new WorkWithDataBase(connection);
        GetVacFromPages getVacFromPages = new GetVacFromPages();
        getVacFromPages.getVacFromPages(workWithDataBase);
        this.closeConnection(connection);
    }

    private Connection getInitConnection() {
        MyConnection myConnection = new MyConnection();
        myConnection.initConnection();
        connection = myConnection.conn;
        return connection;
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGG.error(e.getMessage(), e);
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Sqlru sqlru = new Sqlru();
        sqlru.executeProgram();
    }
}