package pl.hycom.ip2018.searchengine.googlesearch.googlemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleMetaTag {

    @JsonProperty("article:modified_time")
    private String timestamp;
}
