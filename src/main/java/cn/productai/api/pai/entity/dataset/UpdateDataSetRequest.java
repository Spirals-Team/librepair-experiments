package cn.productai.api.pai.entity.dataset;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.ManagementAPIBaseRequest;
import cn.productai.api.core.enums.HttpMethod;

public class UpdateDataSetRequest extends ManagementAPIBaseRequest<UpdateDataSetResponse> {

    @Override
    public Class<UpdateDataSetResponse> getResponseClass() {
        return UpdateDataSetResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/image_sets/_0000014/%s", this.getHost(), this.getDataSetId());
    }

    private String dataSetId;

    @ParaSignAttribute(Name = "name")
    public String name;

    @ParaSignAttribute(Name = "description")
    public String description;

    public String getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(String dataSetId) {
        this.dataSetId = dataSetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UpdateDataSetRequest() {
        super();
        this.setRequestMethod(HttpMethod.PUT);
    }

    /**
     *
     * @param dataSetId 数据集ID
     * @param name 数据集名称
     * @param description 数据集描述
     */
    public UpdateDataSetRequest(String dataSetId, String name, String description) {
        this();
        this.setDataSetId(dataSetId);
        this.setName(name);
        this.setDescription(description);
    }
}
