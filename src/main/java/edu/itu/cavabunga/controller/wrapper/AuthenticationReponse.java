package edu.itu.cavabunga.controller.wrapper;

import edu.itu.cavabunga.core.entity.Authentication;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationReponse {
    public Integer status;
    public String message;
    public List<Authentication> data = new ArrayList<Authentication>();

    public AuthenticationReponse(Integer status, String message, List<Authentication> data) {
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

    public List<Authentication> getData() {
        return data;
    }

    public void setData(List<Authentication> data) {
        this.data = data;
    }
}
