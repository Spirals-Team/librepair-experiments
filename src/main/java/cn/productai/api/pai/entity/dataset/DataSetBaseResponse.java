package cn.productai.api.pai.entity.dataset;

import cn.productai.api.core.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Thinkpad on 2017/7/4.
 *
 */
public class DataSetBaseResponse extends BaseResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("n_downloaded")
    private int downloaedCount;

    @JsonProperty("description")
    private String description;

    @JsonProperty("modified_at")
    private String lastModifiedTime;

    @JsonProperty("created_at")
    private String createdTime;

    @JsonProperty("creator_id")
    private long creatorId;

    @JsonProperty("n_failed")
    private int failedCount;

    @JsonProperty("n_items")
    private int itemsSearchedCount;

    @JsonProperty("id")
    private String dataSetId;

    @JsonProperty("name")
    private String dataSetName;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDownloaedCount() {
        return downloaedCount;
    }

    public void setDownloaedCount(int downloaedCount) {
        this.downloaedCount = downloaedCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public int getItemsSearchedCount() {
        return itemsSearchedCount;
    }

    public void setItemsSearchedCount(int itemsSearchedCount) {
        this.itemsSearchedCount = itemsSearchedCount;
    }

    public String getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(String dataSetId) {
        this.dataSetId = dataSetId;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }
}
