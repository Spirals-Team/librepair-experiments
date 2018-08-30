package com.marsspiders.ukwa.solr.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacetCounts {

    @JsonProperty("facet_fields")
    private FacetFields fields;

    @JsonProperty("facet_ranges")
    private FacetRanges ranges;

    public FacetFields getFields() {
        return fields;
    }

    public void setFields(FacetFields fields) {
        this.fields = fields;
    }

    public FacetRanges getRanges() {
        return ranges;
    }

    public void setRanges(FacetRanges ranges) {
        this.ranges = ranges;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
