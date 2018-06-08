package cn.productai.api.core.exceptions;

/**
 * Created by Zhong Wang on 2017/6/30.
 */
public class ServerException extends ClientException {
    public ServerException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
