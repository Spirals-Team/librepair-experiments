package ru.job4j.jsoup;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.sql.SQLConnect;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Parser {
    private static Logger log = Logger.getLogger(Parser.class);
    private final Setup setup;
    private final SQLConnect connect;
    private final MySQLReq sqlReq;
    private final int hour;
    private final static int DEFAULT_STOP = 10;


    public Parser() {
        this.setup = new Setup("chapter_006/parser.ini");
        if (setup.isFlagProvlem()) {
            connect = null;
            sqlReq = null;
            hour = 0;
            return;
        }
        connect = new SQLConnect(setup.getUrl(), setup.getLogin(), setup.getPassword(), true);
        sqlReq = new MySQLReq(connect.getConnection());
        sqlReq.createTable();
        hour = setup.getHour();
        start();
    }

    private void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long sleep = hour * 3600000;
                while (!Thread.interrupted()) {
                    for (;;) {
                        log.info("Start work");
                        work();
                        if (Thread.currentThread().isInterrupted()) {
                            break;
                        }
                        try {
                            log.info(String.format("Sleep: %d hour", hour));
                            Thread.sleep(sleep);
                        } catch (InterruptedException e) {
                            log.error(e);
                        }
                    }
                    break;
                }
                stop();
            }
        }).start();
    }

    private Date getStopLine() {
        Date stopLine;
        if (sqlReq.isEmptyBD()) {
            stopLine = firstDayOfYear();
//            stopLine = fiveYearsAgo();
        } else {
            stopLine = sqlReq.lastJob();
        }
        return stopLine;
    }

    private void work() {
        Elements elements;
        Date stopLine = getStopLine();
        log.info(String.format("Stop search data: %s", stopLine));
        int countAdd = 0;
        for (String url: setup.getJobUrls()) {
            int indexPage = 1;
            int stopCount = 0;
            do {
                Document document = jsoupConnect(String.format("%s%d", url, indexPage++));
                elements = document.getElementsByClass("postslisttopic");
                for (Element element : elements) {
                    if (isJavaJob(element.text())) {
                        if (!testAndAddUrlJob(element.select("a").attr("href"), stopLine)) {
                            stopCount++;
                        } else {
                            stopCount = 0;
                            countAdd++;
                        }
                    }
                }
                if (stopCount > DEFAULT_STOP) {
                    break;
                }
            } while (elements.size() > 0);
        }
        log.info(String.format("Add: %d", countAdd));
    }

    private boolean testAndAddUrlJob(String url, Date stopLine) {
        Document document = jsoupConnect(url);
        String title = document.title()
                .replace(" / Вакансии / Sql.ru", "")
                .replace(" / Работа / Sql.ru", "");
        Elements elements = document.getElementsByClass("msgBody");
        String text = elements.get(1).text();
        elements = document.getElementsByClass("msgFooter");
        Date time = correctTime(elements.get(0).text());
        if (time.after(stopLine)) {
            sqlReq.add(url, title, text, time);
            return true;
        } else {
            return false;
        }
    }

    private boolean isSPB(String text) {
        text = text.toLowerCase();
        if (text.contains("санкт-петербург")) {
            return true;
        } else if (text.contains("спб")) {
            return true;
        } else if (text.contains("питер")) {
            return true;
        }
        return false;
    }

    private Date correctTime(String time) {
        time = time.substring(0, time.indexOf("[") - 1);
        Date date = null;
        DateFormat format = new SimpleDateFormat("d MMM yy");
        if (time.contains("вчера")) {
            time = time.replace("вчера", format.format(minusDay()));
        } else if (time.contains("сегодня")) {
            time = time.replace("сегодня", format.format(new Date()));
        }
        format = new SimpleDateFormat("d MMM yy, HH:mm");
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            log.error(e);
        }
        return date;
    }

    private Date minusDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    private Date minusHour(int value) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -value);
        return cal.getTime();
    }

    private Date fiveYearsAgo() {
        Calendar cal = new GregorianCalendar(2013, 1, 1);
        return cal.getTime();
    }

    private Date firstDayOfYear() {
        DateFormat format = new SimpleDateFormat("yyyy");
        Date date = null;
        try {
            date = format.parse(format.format(new Date()));
        } catch (ParseException e) {
            log.error(e);
        }
        return date;
    }

    private boolean isJavaJob(String text) {
        if (!text.toLowerCase().contains("java")) {
            return false;
        } else {
            text = text.toLowerCase().replace("java script", "").replace("javascript", "");
        }
        return text.contains("java");
    }

    private void stop() {
        connect.close();
    }

    private Document jsoupConnect(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error(String.format("Error: connect url %s %s", url, e));
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
    }
}
