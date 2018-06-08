package cn.productai.api.pai.entity.batch;

import cn.productai.api.core.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class SearchTaskResponse extends BaseResponse {

    @JsonProperty("data")
    private HashMap<String, TaskDetailInfo> tasks;

    public HashMap<String, TaskDetailInfo> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<String, TaskDetailInfo> tasks) {
        this.tasks = tasks;
    }
}
