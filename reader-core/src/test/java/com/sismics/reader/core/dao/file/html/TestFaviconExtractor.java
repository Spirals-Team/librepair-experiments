package com.sismics.reader.core.dao.file.html;

import com.sismics.reader.core.util.http.ReaderHttpClient;
import junit.framework.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;

/**
 * Test of the favicon extractor.
 * 
 * @author jtremeaux
 */
public class TestFaviconExtractor {
    @Test
    public void faviconExtractorKickstarterTest() throws Exception {
        final FaviconExtractor extractor = new FaviconExtractor("https://www.kickstarter.com/");
        new ReaderHttpClient() {
            
            @Override
            public void process(InputStream is) throws Exception {
                extractor.readPage(is);
            }
        }.open(new URL("https://www.kickstarter.com"));
        Assert.assertTrue(extractor.getFavicon().contains("/favicon.ico"));
    }
}
