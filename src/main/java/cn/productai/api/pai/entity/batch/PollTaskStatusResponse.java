package cn.productai.api.pai.entity.batch;

import cn.productai.api.core.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PollTaskStatusResponse extends BaseResponse {

    @JsonProperty("data")
    private TaskDetailInfo taskInfo;

    public TaskDetailInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskDetailInfo taskInfo) {
        this.taskInfo = taskInfo;
    }
}
