package com.objectia.twostep.exception;

import com.google.gson.*;
import com.objectia.twostep.model.Response2;

public class APIException extends TwostepException {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String body;
    private final int status;
    private final int code;

    public APIException(String body) {
        super();

        Gson gson = new Gson();
        Response2 response = gson.fromJson(body, Response2.class);

        this.message = response.getMessage();
        this.status = response.getStatus();
        this.code = response.getCode();

        this.body = body;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }

}