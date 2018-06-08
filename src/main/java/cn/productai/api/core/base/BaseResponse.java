package cn.productai.api.core.base;

import cn.productai.api.core.enums.ResponseType;
import com.fasterxml.jackson.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Created by Thinkpad on 2017/7/3.
 */
public abstract class BaseResponse {

    private ResponseType responseType = ResponseType.Json;
    private String responseBase64String;
    private Integer statusCode;
    private Map<String, List<String>> headers;
    private String responseJsonString;

    public String getResponseJsonString() throws IOException {
        if (this.getResponseBase64String() != null && !this.getResponseBase64String().isEmpty()) {
            return new String(Base64.getDecoder().decode(this.getResponseBase64String()));
        }
        return "";
    }

    public ResponseType getResponseType(){
        return this.responseType;
    }

    public String getResponseBase64String(){
        return this.responseBase64String;
    }

    public void setResponseBase64String(String base64String){
        this.responseBase64String = base64String;
    }

    public Integer getStatusCode(){
        return this.statusCode;
    }

    public void setStatusCode(Integer httpStatus){
        this.statusCode = httpStatus;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    @JsonProperty("is_err")
    private Integer isError;

    @JsonProperty("err_msg")
    private String errorMsg;

    @JsonProperty("time")
    private Double time;

    @JsonProperty("time_detail")
    private Double[] timeDetails;

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("ver")
    private String version;

    @JsonProperty("msg")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getIsError(){
        return this.isError;
    }

    public void setIsError(Integer value){
        this.isError = value;
    }

    public String getErrorMsg(){
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double[] getTimeDetails() {
        return timeDetails;
    }

    public void setTimeDetails(Double[] timeDetails) {
        this.timeDetails = timeDetails;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public BaseResponse(){

    }
}
