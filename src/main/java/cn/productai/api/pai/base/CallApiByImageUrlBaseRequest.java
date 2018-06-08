package cn.productai.api.pai.base;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.base.BaseResponse;
import cn.productai.api.core.helper.EnumHelper;
import cn.productai.api.core.helper.WebQueryHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class CallApiByImageUrlBaseRequest<T extends BaseResponse> extends BaseRequest<T> {

    private String serviceType;
    private String serviceId;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/%s/%s", this.getHost(), this.getServiceType(), this.getServiceId());
    }

    @Override
    public byte[] getQueryBytes() {
        return null;
    }

    @ParaSignAttribute(Name = "url", IsNeedUrlEncode = true)
    public String url;

    @ParaSignAttribute(Name = "loc")
    public String loc = "0-0-1-1";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public CallApiByImageUrlBaseRequest() {
        super();
    }

    public CallApiByImageUrlBaseRequest(String serviceType, String serviceId) {
        this();
        if (serviceType.isEmpty()) {
            throw new IllegalArgumentException("serviceType can not be null");
        }
        if (serviceId.isEmpty()) {
            throw new IllegalArgumentException("serviceId can not be null");
        }

        this.serviceType = serviceType;
        this.serviceId = serviceId;
    }

    public CallApiByImageUrlBaseRequest(String serviceType, String serviceId, String url) {
        this(serviceType, serviceId);

        this.url = url;
    }

    public CallApiByImageUrlBaseRequest(String serviceType, String serviceId, String url, String loc) {
        this(serviceType, serviceId, url);

        if (loc != null && !loc.isEmpty())
            this.loc = loc;
    }
}
