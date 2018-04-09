package io.recruiter.application.common.networking.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class MessageResponse implements Serializable {
    @Getter
    @Setter
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
