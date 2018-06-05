package pl.hycom.ip2018.searchengine.localsearch.model;

import lombok.Data;
import pl.hycom.ip2018.searchengine.providercontract.SimpleResult;

/**
 * Part of {@link LocalSearchResponse} describes searching results
 */
@Data
public class Result extends SimpleResult {

    public static final String SNIPPET_KEY = "snippet";
    public static final String CREATED_DATE_KEY = "createdDate";
    public static final String MODIFIED_DATE_KEY = "modifiedDate";
    public static final String FILE_TYPE_KEY = "fileType";
    private static final String PROVIDER = "local";

    private String provider = PROVIDER;
}
