package pl.hycom.ip2018.searchengine.googlesearch.model;

import lombok.Data;
import pl.hycom.ip2018.searchengine.providercontract.SimpleResult;

/**
 * Part of {@link GoogleSearchResponse} describes searching results
 */
@Data
public class Result extends SimpleResult {
    public static final String SNIPPET_KEY = "snippet";
    public static final String TIMESTAMP_KEY = "timestamp";
    private static final String PROVIDER = "google";

    private String provider = PROVIDER;
}
