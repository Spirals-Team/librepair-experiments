package cn.productai.api.pai.entity.detect;

import cn.productai.api.core.entity.AIService;
import cn.productai.api.core.helper.EnumHelper;
import cn.productai.api.pai.base.CallApiByImageFileBaseRequest;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Thinkpad on 2017/7/4.
 */
public class DetectByImageFileRequest extends CallApiByImageFileBaseRequest<DetectResponse> {

    @Override
    public Class<DetectResponse> getResponseClass() {
        return DetectResponse.class;
    }

    public DetectByImageFileRequest(String serviceId) {
        super("detect", serviceId);
    }

    public DetectByImageFileRequest(String serviceType, String serviceId) {
        super(serviceType, serviceId);
    }

    public DetectByImageFileRequest(String serviceId, File imageFile, String loc) {
        super("detect", serviceId, imageFile, loc);
    }

    public DetectByImageFileRequest(String serviceType, String serviceId, File imageFile, String loc) {
        super(serviceType, serviceId, imageFile, loc);
    }
}
