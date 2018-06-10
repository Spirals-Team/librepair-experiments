package ru.job4j.jdbc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Parser {

    private boolean flag = true;

    /**
     * Конструктор использует jsoup для парсинга сайта.
     * Распарсиваем нужные поля с сайта, Проверям чтобы заявки были только по Java и не по Javascript
     * так же проверям, чтобы заявки были этого года
     */
    public Parser() {
        ParserJDBC parser = new ParserJDBC();
        Conteiner conteiner = new Conteiner();
        Document doc = null;
        for (int i = 1; flag; i++) {
        try {
            doc = Jsoup.connect("http://www.sql.ru/forum/job-offers/" + i).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements tdTable = doc.getElementsByAttributeValue("class", "forumTable");
            Elements trTable = tdTable.select("tr");
            trTable.forEach(tdElemen -> {
                Elements tdName = tdElemen.select("td[class=postslisttopic]");
                Element tdDate = tdElemen.child(5);
                String url = tdName.select("a").attr("href");
                String text = tdName.select("a").eq(0).text();
                String datePublish = tdDate.text();
                if (text.toLowerCase().contains("java") && !text.toLowerCase().contains("javascript") && !text.toLowerCase().contains("java script")) {
                    if (equalsDate(datePublish)) {
                        int id = Integer.parseInt(url.substring(24, 31));
                        conteiner.setId(id);
                        conteiner.setUrl(url);
                        conteiner.setName(text);
                        parser.add(id, conteiner);
                    }
                }
            });
        }
    }

    /**
     * Метод для определения даты публикации вкансии на сайте
     * Сложность в том, что даты на сайте представлены в виде 12 апр 2018, 20:20
     * наиболее подходящий форматтер для этого определяет даты вида 12 апр. 2018, 20:20
     * та же проблема с месяцами- на сайте это фев, сен, ноя, мая, а форматер распознает
     * февр, сент, нояб, мая
     */
    private boolean equalsDate(String str) {
        boolean result = false;
        if (str.contains("сегодня") || str.contains("вчера")) {
            result = true;
        } else {
            StringBuffer sb = new StringBuffer(str);
            if (str.length() == 16) {
                sb.insert(6, ".");
            } else {
                if (str.length() == 15) {
                    sb.insert(5, ".");
                }
            }
            String s = new String(sb);
            if (s.contains("май")) {
                s = s.replace("й.", "я");
            }
            if (s.contains("фев")) {
                s = s.replace("фев", "февр");
            }
            if (s.contains("сен")) {
                s = s.replace("сен", "сент");
            }
            if (str.contains("ноя")) {
                s = s.replace("ноя", "нояб");
            }
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("dd MMM yy, HH:mm");
            Date date = null;
            try {
                date = format.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            if (year >= 2018) {
                    result = true;
            } else {
                flag = false;
            }
        }
        return result;
    }
}
