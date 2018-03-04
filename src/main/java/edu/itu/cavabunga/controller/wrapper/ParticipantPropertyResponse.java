package edu.itu.cavabunga.controller.wrapper;

import edu.itu.cavabunga.core.entity.ParticipantProperty;

import java.util.ArrayList;
import java.util.List;

public class ParticipantPropertyResponse {
    private Integer status;
    private String message;
    private List<ParticipantProperty> data = new ArrayList<ParticipantProperty>();

    public ParticipantPropertyResponse(Integer status, String message, List<ParticipantProperty> data) {
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

    public List<ParticipantProperty> getData() {
        return data;
    }

    public void setData(List<ParticipantProperty> data) {
        this.data = data;
    }
}
