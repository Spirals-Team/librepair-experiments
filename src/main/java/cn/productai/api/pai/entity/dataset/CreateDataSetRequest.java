package cn.productai.api.pai.entity.dataset;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.ManagementAPIBaseRequest;

public class CreateDataSetRequest extends ManagementAPIBaseRequest<CreateDataSetResponse> {

    @Override
    public Class<CreateDataSetResponse> getResponseClass() {
        return CreateDataSetResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/image_sets/_0000014", this.getHost());
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

    @ParaSignAttribute(Name = "name")
    public String name;

    @ParaSignAttribute(Name = "description")
    public String description;

    /**
     *
     */
    public CreateDataSetRequest(){super();}

    /**
     *
     * @param name the name of the dataset
     * @param description the description of the dataset
     */
    public CreateDataSetRequest(String name,String description) {
        this();
        this.setName(name);
        this.setDescription(description);
    }
}
