package ru.aserdyuchenko.testTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class ConnectionToWebsite {
    private Set<String> offers = new HashSet<>();

    public void getDataFromWebsite() throws Exception {
        String url = "http://sql.ru/forum/job-offers";
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("td.postslisttopic");
        for(Element element : elements) {
            if (isContainJava(element.text().toLowerCase())) {
                this.offers.add(element.text());
            }
        }
    }

    private boolean isContainJava(String text) {
        return  text.contains("java");
    }

    public Set<String> getOffers() {
        return this.offers;
    }
}
