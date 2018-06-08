package cn.productai.api.pai.entity.detect;

import cn.productai.api.core.entity.AIService;
import cn.productai.api.core.helper.EnumHelper;
import cn.productai.api.pai.base.CallApiByImageUrlBaseRequest;

import java.util.HashMap;

/**
 * Created by Thinkpad on 2017/7/4.
 */
public class DetectByImageUrlRequest extends CallApiByImageUrlBaseRequest<DetectResponse> {

    @Override
    public Class<DetectResponse> getResponseClass() {
        return DetectResponse.class;
    }

    public DetectByImageUrlRequest(String serviceId) {
        super("detect", serviceId);
    }

    public DetectByImageUrlRequest(String serviceType, String serviceId) {
        super(serviceType, serviceId);
    }

    public DetectByImageUrlRequest(String serviceId, String url, String loc) {
        super("detect", serviceId, url, loc);
    }

    public DetectByImageUrlRequest(String serviceType, String serviceId, String url, String loc) {
        super(serviceType, serviceId, url, loc);
    }
}
