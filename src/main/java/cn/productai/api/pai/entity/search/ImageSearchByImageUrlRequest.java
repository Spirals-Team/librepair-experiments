package cn.productai.api.pai.entity.search;

import cn.productai.api.core.ITag;
import cn.productai.api.pai.base.SearchByImageUrlBaseRequest;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public class ImageSearchByImageUrlRequest extends SearchByImageUrlBaseRequest<ImageSearchResponse> {

    @Override
    public Class<ImageSearchResponse> getResponseClass() {
        return ImageSearchResponse.class;
    }

    public ImageSearchByImageUrlRequest() {
        super();
    }

    public ImageSearchByImageUrlRequest(String serviceId) {
        super("search", serviceId);
    }

    public ImageSearchByImageUrlRequest(String serviceId, String url, String loc, ITag searchTag, Integer count) {
        super("search", serviceId, url, loc, searchTag, count);
    }
}
