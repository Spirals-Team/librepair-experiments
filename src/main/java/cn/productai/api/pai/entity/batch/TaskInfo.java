package cn.productai.api.pai.entity.batch;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class TaskInfo {

    /**
     * 预计处理时间
     */
    @JsonProperty("estimated_processing_time")
    private Integer estimatedProcessingTime;

    /**
     * 预计等待时间
     */
    @JsonProperty("estimated_waiting_time")
    private Integer estimatedWaitingTime;

    /**
     *  当前任务状态(String)
     *         RECEIVED    任务被接受
     *         PENDING     任务等待中
     *         STARTED     任务启动
     *         SUCCESS     任务执行成功
     *         REVOKED     任务被取消
     *         FAILUR      任务执行失败
     */
    @JsonProperty("state")
    private String state;

    /**
     * 任务唯一ID，后续所有操作都基于该ID进行
     */
    @JsonProperty("task_id")
    private String taskId;

    /**
     * 任务中包含的待处理图片数量
     */
    @JsonProperty("total")
    private Integer total;

    /**
     * 详细错误代码
     */
    @JsonProperty("error_code")
    private String errorCode;

    /**
     * 错误信息
     */
    @JsonProperty("message")
    private String errorMessage;

    /**
     * 返回所有重复的数据
     * key is URL. value is int[]
     */
    @JsonProperty("urls_duplicated")
    private HashMap<String,Integer[]> urlsDuplicated;

    /**
     * 不合法的url列表
     * key is index or row number. value is raw url string
     */
    @JsonProperty("urls_verify_failed")
    private HashMap<Integer,String> urlsVerifyFailed;

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HashMap<String, Integer[]> getUrlsDuplicated() {
        return urlsDuplicated;
    }

    public void setUrlsDuplicated(HashMap<String, Integer[]> urlsDuplicated) {
        this.urlsDuplicated = urlsDuplicated;
    }

    public HashMap<Integer, String> getUrlsVerifyFailed() {
        return urlsVerifyFailed;
    }

    public void setUrlsVerifyFailed(HashMap<Integer, String> urlsVerifyFailed) {
        this.urlsVerifyFailed = urlsVerifyFailed;
    }
}
