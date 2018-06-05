package pl.hycom.ip2018.searchengine.aggregate.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import pl.hycom.ip2018.searchengine.providercontract.ProviderResponse;
import pl.hycom.ip2018.searchengine.providercontract.SimpleResult;
import pl.hycom.ip2018.searchengine.providercontract.service.ProviderSearch;

/**
 * Implementation of {@link AggregateSearch} to get appropriate data type from i.e String query
 */
@Slf4j
@Service
public class AggregateSearch {

    private static final String META_TIME = "time";

    private static final String HTTP_PREFIX = "http://";

    private static final String HTTPS_PREFIX = "https://";

    private static final String PORT_PREFIX = ":";

    @Value("${clients}")
    private String[] providers;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private Decoder decoder;

    @Autowired
    private Encoder encoder;

    /**
     * By submitting a query, we receive a ready answer formed as {@link ProviderResponse} data model based on all registered clients
     *
     * @param query
     *            we are searching for
     * @return ProviderResponse object
     */
    public ProviderResponse getResponse(final String query, final List<String> provider) {

        if (log.isInfoEnabled()) {
            log.info("Requesting aggregate search for [{}]", query);
        }

        final ProviderResponse result = new ProviderResponse();
        result.setResults(Collections.synchronizedList(new ArrayList<>()));

        long startTime = System.currentTimeMillis();

        getClients()
                .parallelStream()
                .forEach(c -> {
                    try {
                        ProviderResponse response = c.getResponse(query);
                        if (response != null && response.getResults() != null) {
                            result.getResults().addAll(response.getResults());
                        }
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("Error during geting response from client", e);
                        }
                    }
                });

        result.setMetadata(ImmutableMap.of(META_TIME, System.currentTimeMillis() - startTime));

        final ProviderResponse output = new ProviderResponse();

        // filtered results
        List<SimpleResult> tmp = new ArrayList<>();

        provider.forEach(providerr -> tmp.addAll(result.getResults().stream().filter(simpleResult ->
                simpleResult.getProvider().toLowerCase().equals(providerr.toLowerCase())).collect(Collectors.toList())));

        output.setMetadata(result.getMetadata());
        output.setResults(tmp);

        return output;
    }

    private Set<ProviderSearch> getClients() {
        return Arrays.stream(providers)
                .parallel()
                .map(provider -> {
                    try {
                        ProviderSearch feignClient = getClientForProvider(provider);

                        if (feignClient != null) {
                            if (log.isInfoEnabled()) {
                                log.info("Feign client for provider[" + provider + "]: " + feignClient);
                            }

                            return feignClient;
                        }

                        if (log.isWarnEnabled()) {
                            log.warn("Feign client for provider[" + provider + "] is null");
                        }

                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("Error during getting client from discovery server", e);
                        }
                    }

                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private ProviderSearch getClientForProvider(String provider) {
        List<ServiceInstance> services = discoveryClient.getInstances(provider);

        if (services.isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("No service available for provider [" + provider + "]");
            }
            return null;
        }

        // get random available instance
        ServiceInstance service = services.get(ThreadLocalRandom.current().nextInt(0, services.size()));

        if (log.isInfoEnabled()) {
            log.info("Found service for provider[" + provider + "]: " + service);
        }

        return Feign
                .builder()
                .contract(new SpringMvcContract())
                .encoder(encoder)
                .decoder(decoder)
                .target(ProviderSearch.class, (service.isSecure() ? HTTPS_PREFIX : HTTP_PREFIX) + service.getHost() + PORT_PREFIX + service.getPort());
    }

}
