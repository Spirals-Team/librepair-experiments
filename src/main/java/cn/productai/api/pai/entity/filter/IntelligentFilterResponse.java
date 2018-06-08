package cn.productai.api.pai.entity.filter;

import cn.productai.api.core.base.BaseResponse;
import cn.productai.api.pai.response.IntelligentFilterResult;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public class IntelligentFilterResponse extends BaseResponse {

    @JsonProperty("results")
    private IntelligentFilterResult[] results;

    @JsonProperty("content_img")
    private String contentImageUrl;

    @JsonProperty("style_img")
    private String styleImagePath;

    public IntelligentFilterResult[] getResults() {
        return results;
    }

    public void setResults(IntelligentFilterResult[] results) {
        this.results = results;
    }

    public String getContentImageUrl() {
        return contentImageUrl;
    }

    public void setContentImageUrl(String contentImageUrl) {
        this.contentImageUrl = contentImageUrl;
    }

    public String getStyleImagePath() {
        return styleImagePath;
    }

    public void setStyleImagePath(String styleImagePath) {
        this.styleImagePath = styleImagePath;
    }
}
