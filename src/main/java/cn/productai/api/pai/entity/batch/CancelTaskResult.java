package cn.productai.api.pai.entity.batch;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CancelTaskResult {

    @JsonProperty("code")
    private String code;

    @JsonProperty("args")
    private String[] args;

    public String getCode() {

        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
