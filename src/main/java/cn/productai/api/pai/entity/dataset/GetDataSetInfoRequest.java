package cn.productai.api.pai.entity.dataset;

import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.enums.HttpMethod;

public class GetDataSetInfoRequest extends BaseRequest<GetDataSetInfoResponse> {

    @Override
    public Class<GetDataSetInfoResponse> getResponseClass() {
        return GetDataSetInfoResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/image_sets/_0000014/%s", this.getHost(), this.getDataSetId());
    }

    private String dataSetId;

    public String getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(String dataSetId) {
        this.dataSetId = dataSetId;
    }

    public GetDataSetInfoRequest() {
        super();
        this.setRequestMethod(HttpMethod.GET);
    }

    /**
     *
     * @param dataSetId 数据集ID
     */
    public GetDataSetInfoRequest(String dataSetId) {
        this();
        this.setDataSetId(dataSetId);
    }
}
