package ru.job4j.sqlru;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.sqlru.items.Url;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class PageScanner implements Runnable {
    private static final String URL = "http://www.sql.ru/forum/job-offers";
    private ArrayBlockingQueue<Url> urlQueue;

    public PageScanner(ArrayBlockingQueue<Url> urlQueue) {
        this.urlQueue = urlQueue;
    }

    @Override
    public void run() {
        Document document;
        try {
            document = Jsoup.connect(URL).get();
            Elements elementsSortOptions = document.getElementsByAttributeValue("class", "sort_options");
            Elements elements = elementsSortOptions.select("tr");
            Element element = elements.get(1);
            int index = Integer.parseInt(element.getElementsByTag("a").eachText().get(9));
            for (int i = index; i >= 1; i--) {
                urlQueue.put(new Url(URL + "/" + i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}