package edu.itu.cavabunga.controller.wrapper;

import edu.itu.cavabunga.core.entity.Property;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PropertyResponse {
    private Integer status;
    private String message;
    private List<Property> data = new ArrayList<Property>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Property> getData() {
        return data;
    }

    public void setData(List<Property> data) {
        this.data = data;
    }

    public void addData(Property property){
        this.data.add(property);
    }

    public void setDataNull(){
        this.data = null;
    }

    public PropertyResponse createPropertyResponseForList(Integer code, String message, List<Property> properties){
        PropertyResponse result = new PropertyResponse();
        result.setStatus(code);
        result.setMessage(message);
        result.setData(properties);
        return result;
    }

    public PropertyResponse createPropertyResponseForSingle(Integer code, String message, Property property){
        PropertyResponse result = new PropertyResponse();
        result.setStatus(code);
        result.setMessage(message);
        result.addData(property);
        return result;
    }

}
