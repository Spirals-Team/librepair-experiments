package com.aliyuncs.fc.request;

import com.aliyuncs.fc.constants.Const;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * used for http invocation
 */
public class HttpInvokeFunctionRequest extends InvokeFunctionRequest {

    private final String path;

    private final String method;

    private final String authType;

    private final Map<String, String> queryParams = new HashMap<String, String>();

    public HttpInvokeFunctionRequest(String serviceName, String functionName, String authType, String method) {
        this(serviceName, functionName, authType, method, "");
    }

    public HttpInvokeFunctionRequest(String serviceName, String functionName, String authType, String method, String path) {
        super(serviceName, functionName);
        this.setInvocationType(Const.INVOCATION_TYPE_HTTP);

        this.path = path == null ? "" : (path.startsWith("/") ? path.substring(1) : path);
        this.authType = authType;
        this.method = method;
    }

    public void addQuery(String name, String value) {
        queryParams.put(name, value);
    }

    @Override
    public Map<String, String> getQueryParams() {
        return queryParams;
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
