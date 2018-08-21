package academy.learnprogramming;

import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;

public class ProxyTest {
    private static final AutomatedBrowserFactory AUTOMATED_BROWSER_FACTORY = new AutomatedBrowserFactory();

    @Test
    public void captureHarFile() {
        final AutomatedBrowser automatedBrowser = AUTOMATED_BROWSER_FACTORY.getAutomatedBrowser("Chrome");
        try {
            automatedBrowser.init();

            automatedBrowser.captureHarFile();

            automatedBrowser.goTo("https://learnprogramming.academy/");
        } finally {
            try {
                automatedBrowser.saveHarFile("test.har");
            } finally {
                automatedBrowser.destroy();
            }
        }
    }

    @Test
    public void captureCompleteHarFile() {
        final AutomatedBrowser automatedBrowser = AUTOMATED_BROWSER_FACTORY.getAutomatedBrowser("Chrome");
        try {
            automatedBrowser.init();

            automatedBrowser.captureCompleteHarFile();

            automatedBrowser.goTo("https://learnprogramming.academy/");
        } finally {
            try {
                automatedBrowser.saveHarFile("test.har");
            } finally {
                automatedBrowser.destroy();
            }
        }
    }

    @Test
    public void modifyRequests() {
        final AutomatedBrowser automatedBrowser = AUTOMATED_BROWSER_FACTORY.getAutomatedBrowser("chrome");

        automatedBrowser.init();

        automatedBrowser.blockRequestTo(".*?\\.png", 201);
        automatedBrowser.blockRequestTo(".*?twitter\\.com.*", 500);
        automatedBrowser.blockRequestTo(".*?google\\.com.*", 500);
        automatedBrowser.blockRequestTo(".*?facebook\\.com.*", 500);

        automatedBrowser.goTo("https://learnprogramming.academy/");
    }

    @Test
    public void mockRequests() throws URISyntaxException {
        final AutomatedBrowser automatedBrowser = AUTOMATED_BROWSER_FACTORY.getAutomatedBrowser("Firefox");
        try {
            automatedBrowser.init();
            automatedBrowser.alterResponseFrom(
                    ".*?query\\.yahooapis\\.com.*",
                    200,
                    "{\"query\":{\"count\":1,\"created\":\"2018-04-28T05:20:30Z\",\"lang\":\"en-US\",\"results\":{\"channel\":{\"astronomy\":{\"sunset\":\"7:00 pm\"}}}}}");
            automatedBrowser.goTo(FormTest.class.getResource("/apitest.html").toURI().toString());
            final String sunset = automatedBrowser.getTextFromElementWithId("sunset", 60);
            Assert.assertTrue(sunset, sunset.contains("7:00 pm"));
        } finally {
            automatedBrowser.destroy();
        }
    }

    @Test
    public void mockRequests2() throws URISyntaxException {
        final AutomatedBrowser automatedBrowser = AUTOMATED_BROWSER_FACTORY.getAutomatedBrowser("Firefox");
        try {
            automatedBrowser.init();
            automatedBrowser.alterResponseFrom(
                    ".*?query\\.yahooapis\\.com.*",
                    200,
                    "{\"query\":{\"count\":1,\"created\":\"2018-04-28T05:20:30Z\",\"lang\":\"en-US\",\"results\":{\"channel\":{\"astronomy\":{\"sunset\":\"7:4 pm\"}}}}}");
            automatedBrowser.goTo(FormTest.class.getResource("/apitest.html").toURI().toString());
            final String sunset = automatedBrowser.getTextFromElementWithId("sunset", 60);
            Assert.assertTrue(sunset, sunset.contains("7:4 pm"));
        } finally {
            automatedBrowser.destroy();
        }
    }
}
