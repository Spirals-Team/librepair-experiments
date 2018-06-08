package cn.productai.api.pai.entity.batch;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskDetailInfo {

    @JsonProperty("csv")
    private String csvFileUrl;

    @JsonProperty("download_urls")
    private String[] downloadUrls;

    @JsonProperty("received_time")
    private String receivedTime;

    @JsonProperty("service_id")
    private String serviceId;

    @JsonProperty("state")
    private String state;

    @JsonProperty("task_id")
    private String taskId;

    @JsonProperty("total")
    private Integer totalCount;

    @JsonProperty("success")
    private Integer successCount;

    @JsonProperty("estimated_processing_time")
    private Integer estimatedProcessingTime;

    @JsonProperty("estimated_waiting_time")
    private Integer estimatedWaitingTime;

    public String getCsvFileUrl() {
        return csvFileUrl;
    }

    public void setCsvFileUrl(String csvFileUrl) {
        this.csvFileUrl = csvFileUrl;
    }

    public String[] getDownloadUrls() {
        return downloadUrls;
    }

    public void setDownloadUrls(String[] downloadUrls) {
        this.downloadUrls = downloadUrls;
    }

    public String getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(String receivedTime) {
        this.receivedTime = receivedTime;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getEstimatedProcessingTime() {
        return estimatedProcessingTime;
    }

    public void setEstimatedProcessingTime(Integer estimatedProcessingTime) {
        this.estimatedProcessingTime = estimatedProcessingTime;
    }

    public Integer getEstimatedWaitingTime() {
        return estimatedWaitingTime;
    }

    public void setEstimatedWaitingTime(Integer estimatedWaitingTime) {
        this.estimatedWaitingTime = estimatedWaitingTime;
    }
}
