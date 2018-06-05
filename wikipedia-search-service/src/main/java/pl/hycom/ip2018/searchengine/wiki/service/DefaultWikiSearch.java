package pl.hycom.ip2018.searchengine.wiki.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.util.UriTemplate;
import pl.hycom.ip2018.searchengine.wiki.converter.WikiResponseConverter;
import pl.hycom.ip2018.searchengine.wiki.exception.WikipediaException;
import pl.hycom.ip2018.searchengine.wiki.model.WikiSearchResponse;
import pl.hycom.ip2018.searchengine.wiki.wikimodel.WikiResponse;


/**
 * Implementation of {@link WikiSearch} to get appropriate data type from i.e String query
 */
@Slf4j
public class DefaultWikiSearch implements WikiSearch {

    @Autowired
    private Environment environment;

    @Autowired
    private WikiResponseConverter wikiResponseConverter;

    @Autowired
    private JsonResponse jsonResponse;

    /**
     * By submitting a query, we receive a ready answer formed as {@link WikiSearchResponse} data model
     * @param query we are searching for
     * @return WikiSearchResponse object
     */
    @Override
    public WikiSearchResponse getResponse(String query) throws WikipediaException {

        if (log.isInfoEnabled()) {
            log.info("Requesting searching results for {}", query);
        }

        try {
            WikiResponse fullResponse =
                    jsonResponse.invoke(
                        new UriTemplate(environment.getProperty("rest.api.baseUrl"))
                                .expand(environment.getProperty("rest.api.srLimit"),
                                        environment.getProperty("rest.api.srInterWiki"), query),
                        WikiResponse.class);

            return wikiResponseConverter.convert(fullResponse);

        } catch (ResourceAccessException | HttpClientErrorException e) {
            if (log.isInfoEnabled()) {
                log.error("Searching results for {} are not available from Wikipedia", query);
            }
        }
        throw new WikipediaException();
    }
}
