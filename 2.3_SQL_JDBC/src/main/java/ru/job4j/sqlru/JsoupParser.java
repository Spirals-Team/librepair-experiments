package ru.job4j.sqlru;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Stack;

/**
 * Class JsoupParser.
 */
public class JsoupParser {
    /**
     * @param LOGGER logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsoupParser.class);
    /**
     * @param time time
     */
    private final Time time;
    /**
     * @param stack stack for vacancies
     */
    private Stack<Vacancy> stack = new Stack<>();
    /**
     * @param wordJava for check
     */
    private final String wordJava = "java";
    /**
     * @param wordJS1 for check
     */
    private final String wordJS1 = "javascript";
    /**
     * @param wordJS2 for check
     */
    private final String wordJS2 = "java script";
    /**
     * @param url url for parsing
     */
    private final String url = "http://www.sql.ru/forum/job-offers/";

    /**
     * Constructor.
     * @param time time
     */
    public JsoupParser(Time time) {
        this.time = time;
    }

    /**
     * Method for parsing site.
     * @return parsing result
     */
    public Stack<Vacancy> startParse() {
        for (int i = 1; i > 0; i++) {
            String tempUrl = url + i;
            Document document = null;
            try {
                document = Jsoup.connect(tempUrl).get();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
            if (!parsePage(document)) {
                break;
            }
        }
        return stack;
    }

    /**
     * Method for parsing page.
     * @param document page for parsing
     * @return result
     */
    private boolean parsePage(Document document) {
        boolean result = true;
        Elements el = document.select("table[class*=forumTable]").select("tr");
        Iterator iterator = el.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            Element e = (Element) iterator.next();
            String s = e.select("a[href]").first().text().toLowerCase();
            if (this.checkJava(s)) {
                String n = e.select("td").last().html();
                LocalDateTime ldt = time.convertString(n);
                if (time.equalsDate(ldt)) {
                    String k = e.select("a[href]").first().attr("href");
                    stack.push(new Vacancy(s, k, ldt));
                } else {
                    result =  false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Method for checking vacancy.
     * @param s vacancy
     * @return result
     */
    private boolean checkJava(String s) {
        return (!s.contains(wordJS1) && !s.contains(wordJS2) && s.contains(wordJava));
    }
}
