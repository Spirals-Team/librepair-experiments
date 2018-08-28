package ru.job4j.sqlru;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.sqlru.bd.DataBase;
import ru.job4j.sqlru.items.Url;
import ru.job4j.sqlru.items.Vacancy;
import ru.job4j.sqlru.setting.LoadSetting;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class AdvertScanner implements Runnable {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d MMM yy, HH:mm");
    private static final SimpleDateFormat DATE_PREPARE = new SimpleDateFormat("d MMM yy");
    private ArrayBlockingQueue<Url> urlQueue;
    private DataBase dataBase;
    private LoadSetting loadSetting;

    public AdvertScanner(ArrayBlockingQueue<Url> urlQueue, DataBase dataBase, LoadSetting loadSetting) {
        this.urlQueue = urlQueue;
        this.dataBase = dataBase;
    }

    @Override
    public void run() {
        do {
            try {
                Url urlObject = urlQueue.poll(10000, TimeUnit.MILLISECONDS);
                Document document = Jsoup.connect(urlObject.getUrl()).get();
                Elements elementsForumTable = document.getElementsByAttributeValue("class", "forumTable");
                Elements elements = elementsForumTable.select("tr");
                for (int i = 4; i < elements.size(); i++) {
                    Element element = elements.get(i);
                    Elements urlAndText = element.getElementsByAttributeValue("class", "postslisttopic");
                    String url = urlAndText.first().getElementsByTag("a").attr("href");
                    String urlText = urlAndText.first().getElementsByTag("a").eachText().get(0);
                    Date date = transferDate(element.getElementsByAttributeValue("class", "altCol").last().text()).getTime();
                    long dateUnix = transferDate(element.getElementsByAttributeValue("class", "altCol").last().text()).getTimeInMillis();
                    String autor = element.getElementsByAttributeValue("class", "altCol").eachText().get(0);
                    if (Pattern.compile("[j,J]ava\\s?(?!\\s?[s,S]cript)").matcher(urlText).find()) {
                        Calendar calendar = Calendar.getInstance();
                        if (dataBase.getMaxDate() == 0) {
                            calendar.set(loadSetting.year, loadSetting.month, loadSetting.date);
                        } else {
                            calendar.setTimeInMillis(dataBase.getMaxDate());
                        }
                        if (dateUnix > calendar.getTimeInMillis() || dateUnix == calendar.getTimeInMillis()) {
                            Document documentText = Jsoup.connect(url).get();
                            Elements elementsForumTableText = documentText.getElementsByAttributeValue("class", "msgTable");
                            Elements elementText = elementsForumTableText.first().getElementsByAttributeValue("class", "msgBody");
                            String text = elementText.eachText().get(1);
                            dataBase.addData(new Vacancy(urlText, url, text, autor, date));
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!urlQueue.isEmpty());
    }

    public Calendar transferDate(String date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            date = date.replaceAll("сегодня", DATE_PREPARE.format(calendar.getTime()));
            calendar.add(Calendar.DATE, -1);
            date = date.replaceAll("вчера", DATE_PREPARE.format(calendar.getTime()));
            try {
                calendar.setTime(DATE_FORMAT.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return calendar;
    }
}
