package edu.itu.cavabunga.controller.wrapper;

import edu.itu.cavabunga.core.entity.Parameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParameterResponse {
    private Integer status;
    private String message;
    private List<Parameter> data = new ArrayList<Parameter>();

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

    public List<Parameter> getData() {
        return data;
    }

    public void setData(List<Parameter> data) {
        this.data = data;
    }

    public void addData(Parameter parameter){
        this.data.add(parameter);
    }

    public void setDataNull(){
        this.data = null;
    }

    public ParameterResponse createParameterResponseForList(Integer code, String message, List<Parameter> parameters){
        ParameterResponse result = new ParameterResponse();
        result.setStatus(code);
        result.setMessage(message);
        result.setData(parameters);
        return result;
    }

    public ParameterResponse createParameterReponseForSingle(Integer code, String message, Parameter parameter){
        ParameterResponse result = new ParameterResponse();
        result.setStatus(code);
        result.setMessage(message);
        result.addData(parameter);
        return result;
    }
}
