package cn.productai.api.pai.entity.productset;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductSet {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("createdAt")
    private String created_at;

    @JsonProperty("creatorId")
    private Integer creator_id;

    @JsonProperty("imageSetId")
    private String image_set_id;

    @JsonProperty("modifiedAt")
    private String modified_at;

    @JsonProperty("status")
    private String status;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(Integer creator_id) {
        this.creator_id = creator_id;
    }

    public String getImage_set_id() {
        return image_set_id;
    }

    public void setImage_set_id(String image_set_id) {
        this.image_set_id = image_set_id;
    }

    public String getModified_at() {
        return modified_at;
    }

    public void setModified_at(String modified_at) {
        this.modified_at = modified_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
