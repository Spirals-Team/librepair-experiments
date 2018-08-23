package ru.job4j.sqlru;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Properties;

/**
 * Class SqlRuParser
 */
public class SqlRuParser implements Job {
    /**
     * @param LOGGER logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlRuParser.class);
    /**
     * @param properties properties
     */
    private Properties properties = new Properties();
    /**
     * @param time time
     */
    private Time time;
    /**
     * @param parser parser
     */
    private JsoupParser parser;
    /**
     * @param database database
     */
    private Database database;
    /**
     * @param path path
     */
    private String path;


    /**
     * Method init.
     */
    public void init() {
        initProperties();
        this.database = new Database(this.properties);
        String temp = properties.getProperty("last.check");
        if (database.size() == 0 || temp == null || temp.equals("")) {
            this.parse();
        } else {
            this.parse(temp);
        }
    }

    /**
     * Method for first parsing.
     */
    private void parse() {
        this.time = new Time();
        time.setFinish(Year.now().atDay(1).atStartOfDay());
        this.parser = new JsoupParser(time);
        database.insertDB(parser.startParse());
        writeProperties(time.convertDate(LocalDateTime.now()));
    }

    /**
     * Method for other parsing.
     *
     * @param s s
     */
    private void parse(String s) {
        this.time = new Time();
        time.setFinish(time.convertString(s));
        this.parser = new JsoupParser(time);
        database.insertDB(parser.startParse());
        writeProperties(time.convertDate(LocalDateTime.now()));
    }

    /**
     * Method for read properties.
     */
    private void initProperties() {
        File file = new File(path);
        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Method for write properties.
     *
     * @param lastCheck for writing
     */
    private void writeProperties(String lastCheck) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            properties.setProperty("last.check", time.convertDate(LocalDateTime.now()));
            properties.store(fos, "last check");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Method execute.
     * @throws JobExecutionException exception
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        path = dataMap.getString("path");
        this.init();
    }

    /**
     * Main method.
     *
     * @param args args.
     * @throws SchedulerException SchedulerException
     */
    public static void main(String[] args) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(SqlRuParser.class).build();
        job.getJobDataMap().put("path", args[0]);

        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 * * ?")).build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}

