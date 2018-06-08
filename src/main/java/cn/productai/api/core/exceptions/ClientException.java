package cn.productai.api.core.exceptions;

/**
 * Created by Zhong Wang on 2017/6/30.
 */
public class ClientException extends Exception {

    private String errorCode;

    private String errorMessage;

    private String requestId;

    public ClientException(String message){
        super(message);
    }

    public ClientException(String errorCode, String errorMessage) {
        super(errorCode + " : " + errorMessage);
        this.setErrorCode(errorCode);
        this.setErrorMessage(errorMessage);
    }

    public ClientException(String errorCode, String errorMessage, String requestId){
        this(errorCode, errorMessage);
        this.setRequestId(requestId);
    }

    public ClientException(Throwable cause){
        super(cause);
    }

    @Override
    public String getMessage(){
        return super.getMessage() + (null == getRequestId() ? "" : "\r\nRequestId :" + getRequestId());
    }

    public String getErrorCode(){
        return errorCode;
    }

    public void setErrorCode(String errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getRequestId(){
        return requestId;
    }

    public void setRequestId(String requestId){
        this.requestId = requestId;
    }
}
