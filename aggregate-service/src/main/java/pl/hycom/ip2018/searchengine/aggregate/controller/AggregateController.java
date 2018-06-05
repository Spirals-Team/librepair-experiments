package pl.hycom.ip2018.searchengine.aggregate.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import pl.hycom.ip2018.searchengine.aggregate.service.AggregateSearch;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;

@RestController
@Api
public class AggregateController {

    @Autowired
    private AggregateSearch aggregateSearch;

    @HystrixCommand(fallbackMethod = "getMessageFallBack", commandKey = "Aggregate-Search-Service", groupKey = "GetMessage")
    @RequestMapping(value = "/res", method = GET)
    public ProviderResponse getMessage(@RequestParam("query") String query,
                                       @RequestParam List<String> provider) {
        return aggregateSearch.getResponse(query, provider);
    }

    public ProviderResponse getMessageFallBack(String query, List<String> providers) {
        return new ProviderResponse(new ArrayList<>());
    }
}


//    @RequestParam("drive") String drive,
//    @RequestParam("wikipedia") String wikipedia,
//    @RequestParam("local") String local