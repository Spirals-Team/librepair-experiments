package edu.itu.cavabunga.controller.wrapper;

import edu.itu.cavabunga.core.entity.Component;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Component
public class ComponentResponse {
    private Integer status;
    private String message;
    private List<Component> data = new ArrayList<Component>();

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

    public List<Component> getData() {
        return data;
    }

    public void setData(List<Component> data) {
        this.data = data;
    }

    public void addData(Component component){
        this.data.add(component);
    }

    public void setDataNull(){
        this.data = null;
    }

    public ComponentResponse createComponentResponseForList(Integer code, String message, List<Component> components){
        ComponentResponse result = new ComponentResponse();
        result.setStatus(code);
        result.setMessage(message);
        result.setData(components);
        return result;
    }

    public ComponentResponse createComponentResponseForSingle(Integer code, String message, Component component){
        ComponentResponse result = new ComponentResponse();
        result.setStatus(code);
        result.setMessage(message);
        result.addData(component);
        return result;
    }
}
