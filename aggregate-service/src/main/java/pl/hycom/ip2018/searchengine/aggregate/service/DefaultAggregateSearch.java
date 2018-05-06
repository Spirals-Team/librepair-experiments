package pl.hycom.ip2018.searchengine.aggregate.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.hycom.ip2018.searchengine.aggregate.intercomm.GoogleClient;
import pl.hycom.ip2018.searchengine.aggregate.intercomm.LocalClient;
import pl.hycom.ip2018.searchengine.aggregate.intercomm.WikiClient;
import pl.hycom.ip2018.searchengine.aggregate.notation.Usage;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;
import pl.hycom.ip2018.searchengine.providercontract.service.ProviderSearch;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link AggregateSearch} to get appropriate data type from i.e String query
 */
@Slf4j
public class DefaultAggregateSearch implements AggregateSearch {

    @Autowired
    Environment environment;

    @Usage
    WikiClient wikiClient;

    @Usage
    GoogleClient googleClient;

    @Usage
    LocalClient localClient;

//    @Usage
//    @Autowired
//    GoogleDriveClient googleDriveClient;


    /**
     * By submitting a query, we receive a ready answer formed as {@link ProviderResponse} data model
     * based on all registered clients
     * @param query we are searching for
     * @return ProviderResponse object
     */
    @Override
    public ProviderResponse getResponse(String query) {

        if (log.isInfoEnabled()) {
            log.info("Requesting aggregate search for {}", query);
        }

        String message = "";
        List<ProviderResponse> output = new ArrayList<>();
        Class<?> obj = DefaultAggregateSearch.class;
            try {
                for(Field field : obj.getDeclaredFields()) {
                    if(field.isAnnotationPresent(Usage.class))  {
                        message = field.getName();
                        if (environment.getProperty("client." + message + ".enabled").equals("true")) {
                            field.setAccessible(true);

                            try {
                                  ProviderSearch value = (ProviderSearch)field.get(this);
                                  output.add(value.getResponse(query));
                            } catch (FeignException e) {
                                if (log.isErrorEnabled()) {
                                    log.error("Client {} hasn't been registered", message);
                                }
                            }

                        }
                    }
                }

            } catch (IllegalAccessException e) {
                if (log.isErrorEnabled()) {
                    log.error("Error has been occurred in client: {}", message);
                }
            }

        return join(output);
    }

    private ProviderResponse join(List<ProviderResponse> partialList) {
        ProviderResponse result = new ProviderResponse();
        result.setResults(new ArrayList<>());
        partialList.forEach(partial ->
                result.getResults().addAll(partial.getResults())
        );
        return result;
    }
}
