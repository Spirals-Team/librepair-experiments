package cn.productai.api.pai.entity.filter;

import cn.productai.api.pai.base.CallApiByImageUrlBaseRequest;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public class IntelligentFilterByImageUrlRequest extends CallApiByImageUrlBaseRequest<IntelligentFilterResponse> {

    @Override
    public Class<IntelligentFilterResponse> getResponseClass() {
        return IntelligentFilterResponse.class;
    }

    public IntelligentFilterByImageUrlRequest() {
        super("filter", "_0000015");
    }

    public IntelligentFilterByImageUrlRequest(String url, String loc) {
        super("filter", "_0000015", loc);
    }
}
