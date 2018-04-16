package com.aliyuncs.fc.request;

import com.aliyuncs.fc.constants.Const;
import com.aliyuncs.fc.constants.HeaderKeys;

import java.util.Map;

import static com.aliyuncs.fc.constants.Const.INVOCATION_TYPE_HTTP;
import static java.lang.String.format;

/**
 * used for http invocation
 */
public class HttpInvokeFunctionRequest extends InvokeFunctionRequest {

    private final String path;

    private final String method;


    private final String authType;

    public HttpInvokeFunctionRequest(String serviceName, String functionName, String authType, String method) {
        this(serviceName, functionName, authType, method, "");
    }

    public HttpInvokeFunctionRequest(String serviceName, String functionName, String authType, String method, String path) {
        super(serviceName, functionName);
        this.setInvocationType(Const.INVOCATION_TYPE_HTTP);
        this.path = path.startsWith("/") ? path.substring(1) : path;
        this.authType = authType;
        this.method = method;
    }

    @Override
    public String getPath() {
        return format(Const.HTTP_INVOKE_FUNCTION_PATH, Const.API_VERSION, getServiceName(), getFunctionName(), path);
    }

    public String getMethod() {
        return method;
    }

    public String getAuthType() {
        return authType;
    }
}
