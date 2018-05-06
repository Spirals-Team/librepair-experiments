package pl.hycom.ip2018.searchengine.aggregate.intercomm;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;
import pl.hycom.ip2018.searchengine.providercontract.service.ProviderSearch;

@FeignClient("google-search-service")
public interface GoogleClient extends ProviderSearch {

    @RequestMapping(method = RequestMethod.GET, value = "/res/{query}")
    ProviderResponse getResponse(@PathVariable("query") String query);

}
