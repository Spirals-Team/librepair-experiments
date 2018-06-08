package cn.productai.api.pai.entity.batch;

import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.enums.HttpMethod;

public class PollTaskStatusRequest extends BaseRequest<PollTaskStatusResponse> {

    @Override
    public Class<PollTaskStatusResponse> getResponseClass() {
        return PollTaskStatusResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/batch/_1000001/task/info/%s", this.getHost(), this.getTaskId());
    }

    private String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public PollTaskStatusRequest() {
        super();
        this.setRequestMethod(HttpMethod.GET);
    }

    public PollTaskStatusRequest(String taskId) {
        this();
        this.setTaskId(taskId);
    }
}
