package cn.productai.api.pai.entity.search;

import cn.productai.api.core.ITag;
import cn.productai.api.pai.base.SearchByImageFileBaseRequest;

import java.io.File;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public class ImageSearchByImageFileRequest extends SearchByImageFileBaseRequest<ImageSearchResponse> {

    @Override
    public Class<ImageSearchResponse> getResponseClass() {
        return ImageSearchResponse.class;
    }

    public ImageSearchByImageFileRequest(String serviceId, File imageFile, String loc, ITag searchTag, Integer count) {
        super("search", serviceId, loc, searchTag, count);
    }

    public ImageSearchByImageFileRequest(String serviceId) {
        super("search", serviceId);
    }
}
