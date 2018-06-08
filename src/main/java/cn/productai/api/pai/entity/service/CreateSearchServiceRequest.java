package cn.productai.api.pai.entity.service;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.ManagementAPIBaseRequest;
import cn.productai.api.core.enums.SearchScenario;
import cn.productai.api.core.helper.EnumHelper;

import java.util.HashMap;

public class CreateSearchServiceRequest extends ManagementAPIBaseRequest<CreateSearchServiceResponse> {

    @Override
    public Class<CreateSearchServiceResponse> getResponseClass() {
        return CreateSearchServiceResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("https://%s/image_sets/_0000014/%s/services", this.getHost(), this.getDataSetId());
    }

    private String dataSetId;

    @ParaSignAttribute(Name = "name")
    public String name;

    private SearchScenario Scenario;

    @ParaSignAttribute(Name = "scenario")
    public String scenarioString;

    public String getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(String dataSetId) {
        this.dataSetId = dataSetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SearchScenario getScenario() {
        return Scenario;
    }

    public void setScenario(SearchScenario scenario) {
        this.Scenario = scenario;
        this.scenarioString = _dics.get(this.getScenario().ordinal());
    }

    private static HashMap<Integer, String> _dics = EnumHelper.toHashMap(SearchScenario.class);

    public CreateSearchServiceRequest() {
        super();
    }

    /**
     * @param imageSetId 数据集ID
     * @param name       搜索服务名字
     * @param scenario   搜索服务场景
     */
    public CreateSearchServiceRequest(String imageSetId, String name, SearchScenario scenario) {
        this();
        this.setDataSetId(imageSetId);
        this.setName(name);
        this.setScenario(scenario);
    }

    /**
     *
     * @param imageSetId    数据集ID
     * @param name          搜索服务名字
     * @param scenarioName  搜索服务场景
     */
    public CreateSearchServiceRequest(String imageSetId, String name, String scenarioName) {
        this();
        this.setDataSetId(imageSetId);
        this.setName(name);
        this.scenarioString = scenarioName;
    }
}
