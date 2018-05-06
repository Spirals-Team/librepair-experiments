package pl.hycom.ip2018.searchengine.wiki.wikimodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WikiResponseData {

    @JsonProperty("search")
    private List<WikiItem> search;

    @JsonProperty("interwikisearch")
    private Map<String, List<WikiItem>> interWikiSearch;

}
