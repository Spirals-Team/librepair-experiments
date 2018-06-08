package cn.productai.api.pai.entity.batch;

import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.enums.HttpMethod;

public class GetSupportServicesRequest extends BaseRequest<GetSupportServicesResponse> {

    @Override
    public Class<GetSupportServicesResponse> getResponseClass() {
        return GetSupportServicesResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/batch/_1000001/services", this.getHost());
    }

    public  GetSupportServicesRequest(){
        super();
        this.setRequestMethod(HttpMethod.GET);
    }
}
