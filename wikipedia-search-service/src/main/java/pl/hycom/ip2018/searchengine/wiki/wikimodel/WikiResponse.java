package pl.hycom.ip2018.searchengine.wiki.wikimodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WikiResponse {

    @JsonProperty("query")
    private WikiResponseData query;

}
