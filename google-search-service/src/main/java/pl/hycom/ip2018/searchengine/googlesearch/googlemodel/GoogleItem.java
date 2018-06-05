package pl.hycom.ip2018.searchengine.googlesearch.googlemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleItem {

    @JsonProperty("title")
    private String header;

    @JsonProperty("snippet")
    private String snippet;

    @JsonProperty("link")
    private String url;

    @JsonProperty("pagemap")
    private GooglePageMap pageMap;
}
