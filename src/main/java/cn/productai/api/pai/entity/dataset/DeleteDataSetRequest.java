package cn.productai.api.pai.entity.dataset;

import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.enums.HttpMethod;

public class DeleteDataSetRequest extends BaseRequest<DeleteDataSetResponse> {

    @Override
    public Class<DeleteDataSetResponse> getResponseClass() {
        return DeleteDataSetResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/image_sets/_0000014/%s", this.getHost(), this.getDataSetId());
    }

    private String DataSetId;

    public String getDataSetId() {
        return DataSetId;
    }

    public void setDataSetId(String dataSetId) {
        DataSetId = dataSetId;
    }

    public DeleteDataSetRequest() {
        super();
        this.setRequestMethod(HttpMethod.DELETE);
    }

    /**
     *
     * @param dataSetId 数据集ID
     */
    public DeleteDataSetRequest(String dataSetId) {
        this();
        this.setDataSetId(dataSetId);
    }
}
