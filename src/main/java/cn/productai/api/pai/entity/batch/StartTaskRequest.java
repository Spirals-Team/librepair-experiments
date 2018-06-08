package cn.productai.api.pai.entity.batch;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.BaseRequest;

public class StartTaskRequest extends BaseRequest<StartTaskResponse> {

    @Override
    public Class<StartTaskResponse> getResponseClass() {
        return StartTaskResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/batch/_1000001/task/apply", this.getHost());
    }

    @ParaSignAttribute(Name = "task_id")
    public String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public StartTaskRequest() {
        super();
    }

    public StartTaskRequest(String taskId) {
        this();
        this.setTaskId(taskId);
    }
}
