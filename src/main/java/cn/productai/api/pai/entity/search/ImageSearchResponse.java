package cn.productai.api.pai.entity.search;

import cn.productai.api.core.base.BaseResponse;
import cn.productai.api.pai.response.SearchResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public class ImageSearchResponse extends BaseResponse {

    @JsonProperty("results")
    private SearchResult[] results;

    @JsonProperty("ds")
    private String dataSetIndexVersion;

    @JsonProperty("searchtime")
    private double searchTime;

    @JsonProperty("loc")
    private Integer[] location;

    @JsonProperty("download_time")
    private double downloadTime;

    @JsonProperty("most_common_tags")
    private List<Object[]> mostCommonTags;

    public SearchResult[] getResults() {
        return results;
    }

    public void setResults(SearchResult[] results) {
        this.results = results;
    }

    public String getDataSetIndexVersion() {
        return dataSetIndexVersion;
    }

    public void setDataSetIndexVersion(String dataSetIndexVersion) {
        this.dataSetIndexVersion = dataSetIndexVersion;
    }

    public double getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(double searchTime) {
        this.searchTime = searchTime;
    }

    public Integer[] getLocation() {
        return location;
    }

    public void setLocation(Integer[] location) {
        this.location = location;
    }

    public double getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(double downloadTime) {
        this.downloadTime = downloadTime;
    }

    public List<Object[]> getMostCommonTags() {
        return mostCommonTags;
    }

    public void setMostCommonTags(List<Object[]> mostCommonTags) {
        this.mostCommonTags = mostCommonTags;
    }
}
