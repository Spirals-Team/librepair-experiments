package com.aliyuncs.fc.request;

import com.aliyuncs.fc.constants.Const;
import com.aliyuncs.fc.constants.HeaderKeys;
import com.aliyuncs.fc.utils.UriBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static com.aliyuncs.fc.constants.Const.INVOCATION_TYPE_HTTP;
import static java.lang.String.format;

/**
 * used for http invocation
 */
public class HttpInvokeFunctionRequest extends InvokeFunctionRequest {

    private final UriBuilder uriBuilder;

    private final String method;

    private final String authType;

    public HttpInvokeFunctionRequest(String serviceName, String functionName, String authType, String method) {
        this(serviceName, functionName, authType, method, "");
    }

    public HttpInvokeFunctionRequest(String serviceName, String functionName, String authType, String method, String path) {
        super(serviceName, functionName);
        this.setInvocationType(Const.INVOCATION_TYPE_HTTP);

        try {
            uriBuilder = UriBuilder.fromUri(
                    new URI(format(Const.HTTP_INVOKE_FUNCTION_PATH,
                            Const.API_VERSION,
                            getServiceName(),
                            getFunctionName(),
                            path.startsWith("/") ? path.substring(1) : path)));
        } catch (URISyntaxException e) {
            throw new RuntimeException("path is not valid.", e);
        }

        this.authType = authType;
        this.method = method;
    }

    public void addQuery(String name, String value) {
        uriBuilder.queryParam(name, value);
    }

    @Override
    public String getPath() {
        return uriBuilder.build();
}

    public String getMethod() {
        return method;
    }

    public String getAuthType() {
        return authType;
    }
}
