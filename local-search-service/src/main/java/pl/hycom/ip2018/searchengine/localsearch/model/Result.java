package pl.hycom.ip2018.searchengine.localsearch.model;

import lombok.Data;
import pl.hycom.ip2018.searchengine.providercontract.SimpleResult;

/**
 * Part of {@link LocalSearchResponse} describes searching results
 */
@Data
public class Result extends SimpleResult {

    private static final String PROVIDER = "local";

    private String provider = PROVIDER;
}
