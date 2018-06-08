package cn.productai.api.pai.entity.batch;

import cn.productai.api.core.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTaskByFileResponse extends BaseResponse {

    @JsonProperty("data")
    private TaskInfo taskInfo;

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }
}
