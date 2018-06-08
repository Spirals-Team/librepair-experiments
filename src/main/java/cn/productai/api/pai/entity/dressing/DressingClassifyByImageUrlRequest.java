package cn.productai.api.pai.entity.dressing;

import cn.productai.api.pai.base.CallApiByImageUrlBaseRequest;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public class DressingClassifyByImageUrlRequest extends CallApiByImageUrlBaseRequest<DressingClassifyResponse> {

    @Override
    public Class<DressingClassifyResponse> getResponseClass() {
        return DressingClassifyResponse.class;
    }

    public DressingClassifyByImageUrlRequest(String loc) {
        super("dressing", "_0000111", loc);
    }

    public DressingClassifyByImageUrlRequest(String url, String loc) {
        this(loc);
        this.setUrl(url);
    }
}
