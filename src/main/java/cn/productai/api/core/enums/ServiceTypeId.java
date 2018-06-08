package cn.productai.api.core.enums;

/**
 * Created by Zhong Wang on 2017/7/5.
 * ServiceType
 */
public enum ServiceTypeId {
    PRODUCT_SET("_0000178");

    private String serviceTypeId;

    ServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    @Override
    public String toString() {
        return this.getServiceTypeId();
    }
}
