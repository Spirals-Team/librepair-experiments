package pl.hycom.ip2018.searchengine.wiki.service;
import pl.hycom.ip2018.searchengine.wiki.exception.WikipediaException;
import pl.hycom.ip2018.searchengine.wiki.model.WikiSearchResponse;

/**
 * Interface specifying a usage of WikiSearch.
 */
public interface WikiSearch{
    /**
     * By submitting a query, we receive a ready answer in the format of our data model
     * @param query we are searching for
     * @return WikiSearchResponse object with mapped data from HTTP response
     */
    WikiSearchResponse getResponse(String query) throws WikipediaException;
}
