package pl.hycom.ip2018.searchengine.googlesearch.model;

import lombok.Data;
import pl.hycom.ip2018.searchengine.providercontract.SimpleResult;

/**
 * Part of {@link GoogleSearchResponse} describes searching results
 */
@Data
public class Result extends SimpleResult {

    private static final String PROVIDER = "google";

    private String provider = PROVIDER;
}
