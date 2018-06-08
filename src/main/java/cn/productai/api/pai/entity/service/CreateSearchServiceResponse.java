package cn.productai.api.pai.entity.service;

import cn.productai.api.core.base.BaseResponse;
import cn.productai.api.core.enums.ServiceStatus;
import cn.productai.api.core.helper.EnumHelper;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class CreateSearchServiceResponse extends BaseResponse {

    // 创建UTC时间，精确到秒
    // 字符串（年-月-日T小时:分钟:秒Z）
    @JsonProperty("created_at")
    private String CreatedTime;

    // 创建者ID
    @JsonProperty("creator_id")
    private long CreatorId;

    // 搜索服务唯一ID，后续操作都需要基于此ID进行
    @JsonProperty("id")
    private String ServiceId ;

    // 搜索服务基于的数据集ID
    @JsonProperty("image_set_id")
    private String ImageSetId ;

    // 索引完成率 = 索引的图片数 / 下完成的图片数
    @JsonProperty("indexed_ratio")
    private float IndexedRatio ;

    // 搜索服务最后一次索引部署时间
    @JsonProperty("last_indexed_time")
    private String LastIndexedTime ;

    // 最后修改UTC时间，精确到秒
    @JsonProperty("last_updated_at")
    private String LastUpdatedTime ;

    // 当前搜索服务线上索引量
    @JsonProperty("n_indexed")
    private long IndexedCount ;

    // 搜索服务名称
    @JsonProperty("name")
    private String Name ;

    @JsonProperty("scenario")
    public String Scenario ;

    // 目前搜索服务状态，只有running
    @JsonProperty("status")
    private String StatusString ;

    private static HashMap<Integer, String> _dics = EnumHelper.toHashMap(ServiceStatus.class);

    // 目前搜索服务状态，只有running
    private ServiceStatus Status;

    @JsonProperty("status_duration")
    private int StatusDuration;

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public long getCreatorId() {
        return CreatorId;
    }

    public void setCreatorId(long creatorId) {
        CreatorId = creatorId;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getImageSetId() {
        return ImageSetId;
    }

    public void setImageSetId(String imageSetId) {
        ImageSetId = imageSetId;
    }

    public float getIndexedRatio() {
        return IndexedRatio;
    }

    public void setIndexedRatio(float indexedRatio) {
        IndexedRatio = indexedRatio;
    }

    public String getLastIndexedTime() {
        return LastIndexedTime;
    }

    public void setLastIndexedTime(String lastIndexedTime) {
        LastIndexedTime = lastIndexedTime;
    }

    public String getLastUpdatedTime() {
        return LastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        LastUpdatedTime = lastUpdatedTime;
    }

    public long getIndexedCount() {
        return IndexedCount;
    }

    public void setIndexedCount(long indexedCount) {
        IndexedCount = indexedCount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getScenario() {
        return Scenario;
    }

    public void setScenario(String scenario) {
        Scenario = scenario;
    }

    public ServiceStatus getStatus() {
        try {
            for (Integer key : _dics.keySet()) {
                if (_dics.get(key).equals(this.getStatusString()))
                    return ServiceStatus.values()[key];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ServiceStatus.UnKnown;
    }

    public int getStatusDuration() {
        return StatusDuration;
    }

    public void setStatusDuration(int statusDuration) {
        StatusDuration = statusDuration;
    }

    public String getStatusString() {
        return StatusString;
    }

    public void setStatusString(String statusString) {
        StatusString = statusString;
    }
}
