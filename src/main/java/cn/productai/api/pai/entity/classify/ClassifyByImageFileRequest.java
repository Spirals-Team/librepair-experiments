package cn.productai.api.pai.entity.classify;

import cn.productai.api.core.entity.AIService;
import cn.productai.api.core.helper.EnumHelper;
import cn.productai.api.pai.base.CallApiByImageFileBaseRequest;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Thinkpad on 2017/7/4.
 */
public class ClassifyByImageFileRequest extends CallApiByImageFileBaseRequest<ClassifyResponse> {

    @Override
    public Class<ClassifyResponse> getResponseClass() {
        return ClassifyResponse.class;
    }

    public ClassifyByImageFileRequest(String serviceType, String serviceId) {
        super(serviceType, serviceId);
    }

    public ClassifyByImageFileRequest(String serviceId) {
        super("classify", serviceId);
    }

    public ClassifyByImageFileRequest(String serviceType, String serviceId, File imageFile) {
        super(serviceType, serviceId, imageFile);
    }

    public ClassifyByImageFileRequest(String serviceId, File imageFile) {
        super("classify", serviceId, imageFile);
    }
}
