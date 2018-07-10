package ru.job4j.interview;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * PageGrabber class. Grab the whole source of HTML page.
 */
public class PageGrabber {

    private static final Logger LOG = Logger.getLogger(PageGrabber.class);

    /**
     * Capture source of HTML page
     *
     * @param url of html page
     * @return document
     */
    public Document capturePage(final String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return doc;
    }
}
