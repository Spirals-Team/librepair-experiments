package cn.productai.api.pai.entity.productset;

import cn.productai.api.pai.entity.dataset.DataSetBaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateProductSetResponse extends DataSetBaseResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
