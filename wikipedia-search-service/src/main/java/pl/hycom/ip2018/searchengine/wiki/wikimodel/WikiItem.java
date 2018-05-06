package pl.hycom.ip2018.searchengine.wiki.wikimodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WikiItem {

    @JsonProperty("title")
    private String header;

    @JsonProperty("snippet")
    private String snippet;

    @JsonProperty("timestamp")
    private String timestamp;
}
