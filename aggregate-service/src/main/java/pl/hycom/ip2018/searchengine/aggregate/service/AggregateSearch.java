package pl.hycom.ip2018.searchengine.aggregate.service;

import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;

import java.util.List;

/**
 * Interface specify usage of different api
 */
public interface AggregateSearch {

    /**
     * Returns response for reachable providers
     *
     * @param query search parameter from user
     * @return ProviderResponse
     */
    ProviderResponse getResponse(String query);
}
