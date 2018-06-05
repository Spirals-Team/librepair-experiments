package pl.hycom.ip2018.searchengine.localsearch.service;

import pl.hycom.ip2018.searchengine.localsearch.exception.LocalSearchException;
import pl.hycom.ip2018.searchengine.localsearch.model.LocalSearchResponse;
import pl.hycom.ip2018.searchengine.providercontract.service.ProviderSearch;

/**
 * Interface specify usage of local disk
 */
public interface LocalSearch {

    /**
     * Returns response wrapped in our type
     *
     * @param query we are searching for
     * @return object representation of response
     */
    LocalSearchResponse getResponse(String query) throws LocalSearchException;
}
