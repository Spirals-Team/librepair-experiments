/*
 * WebCrawler.java
 *
 * Created on 07 September 2005, 12:31
 */

package coaching.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebCrawler Class.
 */
public class WebCrawler extends ThreadTemplate {

    public static final int SEARCH_LIMIT = 50;
    public static final String SEARCH = "Search";
    public static final String STOP = "Stop";
    public static final String DISALLOW = "Disallow:";
    private static final Logger log = LoggerFactory.getLogger(WebCrawler.class);
    private Vector<?> vectorToSearch;
    private Vector<?> vectorSearched;
    private Vector<?> vectorMatches;
    private Thread searchThread;
    private final String textURL = "http://127.0.0.1:8080";

    @Override
    public void execute() {
        try {
            final URL url = new URL(this.textURL);
            if (isHttp(url)) {
                crawlSite(url);
            }
        } catch (final Exception exception) {
            log.error(exception.toString());
        }
    }

    protected boolean isHttp(final URL url) {
        return url.getProtocol().compareTo("http") == 0;
    }

    /**
     * Crawl site.
     *
     * base url
     */
    void crawlSite(final URL baseUrl) {
        final String baseHost = baseUrl.getHost();

        // form URL for any ROBOTS.TXT file
        final String robotsTxtFile = String.format("%s/robots.txt", baseUrl);
        URL robotsUrl = null;
        try {
            robotsUrl = new URL(robotsTxtFile);

            String robotTxtContent = new String();
            final StringBuffer robotTxtContentBuffer = new StringBuffer();
            try {
                // * ROBOT.TXT file one buffer at a time.
                final byte buffer[] = new byte[1024];
                final InputStream robotsTxtInputStream = robotsUrl.openStream();
                int bufferContentSize = 0;
                do {
                    bufferContentSize = robotsTxtInputStream.read(buffer);
                    if (bufferContentSize != -1) {
                        robotTxtContent = robotTxtContent.concat(new String(buffer, 0, bufferContentSize));
                        robotTxtContentBuffer.append(Arrays.toString(buffer));
                    }
                } while (bufferContentSize != -1);

                robotsTxtInputStream.close();
            } catch (final IOException ioException) {
                log.error("{}", ioException.toString());
            }

            safeCrawlSite(baseUrl, robotTxtContent);
        } catch (final MalformedURLException malformedURLException) {
            log.error("{}", malformedURLException.toString());
        }
    }

    /**
     * Safe crawl site.
     *
     * base url
     * robot txt content
     */
    public void safeCrawlSite(final URL baseUrl, final String robotTxtContent) {
        final String baseHost = baseUrl.getHost();

        // search ROBOTS.TXT for "Disallow:" commands.
        final String baseFile = baseUrl.getFile();

        int index = 0;
        while ((index = robotTxtContent.indexOf(DISALLOW, index)) != -1) {
            index += DISALLOW.length();
            final String disallowStatement = robotTxtContent.substring(index);
            final StringTokenizer st = new StringTokenizer(disallowStatement);

            if (!st.hasMoreTokens()) {
                break;
            }

            final String disallowedPath = st.nextToken();

            // * URL starts with a disallowed path, it is not safe
            if (baseFile.indexOf(disallowedPath) == 0) {
                return;
            } else {
                // do stuff
                slowCrawlUrl(baseUrl);
            }
        }
    }

    /**
     * Slow crawl url.
     *
     * base url
     */
    public void slowCrawlUrl(final URL baseUrl) {
        try {
            // * URL
            final URLConnection urlConnection = baseUrl.openConnection();
            urlConnection.setAllowUserInteraction(false);

            final InputStream urlStream = baseUrl.openStream();
            final String mimeType = URLConnection.guessContentTypeFromStream(urlStream);

            if (mimeType != null) {
                if (mimeType.compareTo("text/html") == 0) {
                    // * input stream for links
                    // * entire URL
                    String responseContent = new String();
                    final StringBuffer responseContentBuffer = new StringBuffer();
                    try {
                        final InputStream inputStream = baseUrl.openStream();
                        // * resource one buffer at a time.
                        final byte buffer[] = new byte[1024];
                        int bufferContentSize = 0;
                        do {
                            bufferContentSize = inputStream.read(buffer);
                            if (bufferContentSize != -1) {
                                responseContent = responseContent.concat(new String(buffer, 0, bufferContentSize));
                                responseContentBuffer.append(Arrays.toString(buffer));
                            }
                        } while (bufferContentSize != -1);

                        inputStream.close();

                        final String lowerCaseResponseContent = responseContent.toLowerCase();

                    } catch (final IOException ioException) {
                        // if there is no robots.txt file, it is OK to search
                        log.error("{}", ioException);
                    }
                }
            }
        } catch (final Exception exception) {
            log.error("{}", exception);
        }
    }

    /**
     * main method.
     *
     * arguments
     */
    public static void main(final String args[]) {
        new WebCrawler().start();
    }

}
