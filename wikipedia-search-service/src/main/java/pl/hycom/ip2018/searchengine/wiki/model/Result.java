package pl.hycom.ip2018.searchengine.wiki.model;
import lombok.Data;
import pl.hycom.ip2018.searchengine.providercontract.SimpleResult;

/**
 * Part of {@link WikiSearchResponse} describes searching results
 */

@Data
public class Result extends SimpleResult {
    public static final String SNIPPET_KEY = "snippet";
    public static final String TIMESTAMP_KEY = "timestamp";
    public static final String PROVIDER = "wikipedia";
}
