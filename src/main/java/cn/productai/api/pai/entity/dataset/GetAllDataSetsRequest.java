package cn.productai.api.pai.entity.dataset;

import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.enums.HttpMethod;

public class GetAllDataSetsRequest extends BaseRequest<GetAllDataSetsResponse> {

    @Override
    public Class<GetAllDataSetsResponse> getResponseClass() {
        return GetAllDataSetsResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/image_sets/_0000014", this.getHost());
    }

    public GetAllDataSetsRequest() {
        super();
        this.setRequestMethod(HttpMethod.GET);
    }
}
