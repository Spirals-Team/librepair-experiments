package pl.hycom.ip2018.searchengine.googledrivesearch.model;

import lombok.Data;
import pl.hycom.ip2018.searchengine.providercontract.SimpleResult;

@Data
public class Result extends SimpleResult {

    private static final String PROVIDER = "googledrive";

    private String provider = PROVIDER;
}
