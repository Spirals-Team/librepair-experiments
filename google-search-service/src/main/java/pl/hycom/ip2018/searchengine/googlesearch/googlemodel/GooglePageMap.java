package pl.hycom.ip2018.searchengine.googlesearch.googlemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GooglePageMap {

    @JsonProperty("metatags")
    private List<GoogleMetaTag> metaTags;
}
