package pl.hycom.ip2018.searchengine.providercontract.service;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;

public interface ProviderSearch {

    ProviderResponse getResponse(String query);
}
