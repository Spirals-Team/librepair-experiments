package pl.hycom.ip2018.searchengine.googlesearch.service;

import pl.hycom.ip2018.searchengine.googlesearch.exception.GoogleSearchException;
import pl.hycom.ip2018.searchengine.googlesearch.model.GoogleSearchResponse;
import pl.hycom.ip2018.searchengine.providercontract.service.ProviderSearch;

/**
 * Interface specify usage of google api
 */
public interface GoogleSearch {

    /**
     * Returns response wrapped in our type
     *
     * @param query search parameter from user
     * @return GoogleSearchResponse
     */
    GoogleSearchResponse getResponse(String query) throws GoogleSearchException;
}
