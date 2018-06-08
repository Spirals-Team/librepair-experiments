package cn.productai.api.pai.entity.batch;

import cn.productai.api.core.base.BaseRequest;

public class CancelTaskRequest extends BaseRequest<CancelTaskResponse> {

    @Override
    public String getApiUrl() {
        return String.format("https://%s/batch/_1000001/task/revoke/%s", this.getHost(), this.getTaskId());
    }

    @Override
    public Class<CancelTaskResponse> getResponseClass() {
        return CancelTaskResponse.class;
    }

    private String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public CancelTaskRequest() {
        super();
    }

    public CancelTaskRequest(String taskId) {
        this();
        this.setTaskId(taskId);
    }
}
