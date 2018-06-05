package pl.hycom.ip2018.searchengine.providercontract.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;

public interface ProviderSearch {

    @RequestMapping(method = RequestMethod.GET, value = "/res/{query}")
    ProviderResponse getResponse(@PathVariable("query") String query);
}
