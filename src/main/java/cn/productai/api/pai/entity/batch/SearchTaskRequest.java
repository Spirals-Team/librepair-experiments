package cn.productai.api.pai.entity.batch;

import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.enums.HttpMethod;

public class SearchTaskRequest extends BaseRequest<SearchTaskResponse> {

    @Override
    public Class<SearchTaskResponse> getResponseClass() {
        return SearchTaskResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/batch/_1000001/tasks?start=%s&end=%s", this.getHost(), this.getStartDate(), this.getEndDate());
    }

    private String startDate;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public SearchTaskRequest() {
        super();
        this.setRequestMethod(HttpMethod.GET);
    }

    public SearchTaskRequest(String start, String end) {
        this();
        this.setStartDate(start);
        this.setEndDate(end);
    }
}
