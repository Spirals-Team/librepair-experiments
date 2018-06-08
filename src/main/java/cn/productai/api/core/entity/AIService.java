package cn.productai.api.core.entity;

/**
 * Created by Zhong Wang on 2017/7/3.
 */
public class AIService {
    private String _name;
    private String _serviceType;
    private String _serviceId;

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public String getServiceType(){
        return this._serviceType;
    }

    public void setServiceType(String serviceType){
        this._serviceType = serviceType;
    }

    public String getServiceId(){
        return this._serviceId;
    }

    public void setServiceId(String serviceId){
        this._serviceId = serviceId;
    }
}
