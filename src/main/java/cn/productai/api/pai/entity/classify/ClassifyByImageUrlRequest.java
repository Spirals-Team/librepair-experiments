package cn.productai.api.pai.entity.classify;

import cn.productai.api.core.entity.AIService;
import cn.productai.api.core.helper.EnumHelper;
import cn.productai.api.pai.base.CallApiByImageUrlBaseRequest;

import java.util.HashMap;

/**
 * Created by Thinkpad on 2017/7/4.
 */
public class ClassifyByImageUrlRequest extends CallApiByImageUrlBaseRequest<ClassifyResponse> {

    @Override
    public Class<ClassifyResponse> getResponseClass() {
        return ClassifyResponse.class;
    }

    public ClassifyByImageUrlRequest(String serviceType, String serviceId) {
        super(serviceType, serviceId);
    }

    public ClassifyByImageUrlRequest(String serviceId) {
        super("classify", serviceId);
    }

    public ClassifyByImageUrlRequest(String serviceType, String serviceId, String imageUrl) {
        super(serviceType, serviceId, imageUrl);
    }
}
