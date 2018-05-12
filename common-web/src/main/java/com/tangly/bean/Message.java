package com.tangly.bean;

import java.io.Serializable;

/**
 * webSocket 消息类
 * @author tangly
 */
public class Message implements Serializable {
    private String msg;

    public Message() {
    }

    public Message(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msg='" + msg + '\'' +
                '}';
    }
}