package edu.itu.cavabunga.controller.wrapper;

import edu.itu.cavabunga.core.entity.ComponentProperty;

import java.util.ArrayList;
import java.util.List;

public class ComponentPropertyResponse {
    public Integer status;
    public String message;
    public List<ComponentProperty> data = new ArrayList<ComponentProperty>();

    public ComponentPropertyResponse(Integer status, String message, List<ComponentProperty> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

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

    public List<ComponentProperty> getData() {
        return data;
    }

    public void setData(List<ComponentProperty> data) {
        this.data = data;
    }
}
