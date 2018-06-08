package cn.productai.api.core;

import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.base.BaseResponse;
import cn.productai.api.core.enums.ResponseType;
import cn.productai.api.core.exceptions.ClientException;
import cn.productai.api.core.exceptions.ServerException;
import cn.productai.api.core.helper.RequestHelper;
import cn.productai.api.core.internal.HttpResponse;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultProductAIClient implements IWebClient {

    private String scheme = "https";
    private String host = "api.productai.cn";
    private Charset charset = Charset.forName("UTF-8");
    private IProfile profile = null;

    public DefaultProductAIClient(){

    }

    public DefaultProductAIClient(IProfile profile, String endpoint) {
        this(profile);
        this.setHost(endpoint);
    }

    public DefaultProductAIClient(IProfile profile) {
        this.profile = profile;
    }

    @Override
    public String getScheme() {
        return scheme;
    }

    @Override
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public void setEncoding(Charset charset) {
        this.charset = charset;
    }

    @Override
    public Charset getEncoding() {
        return this.charset;
    }

    @Override
    public void setProfile(IProfile profile) {
        this.profile = profile;
    }

    @Override
    public IProfile getProfile() {
        return this.profile;
    }

    @Override
    public <T extends BaseResponse> T getResponse(BaseRequest<T> request) throws Exception {
        setHostAndSignature(request);

        HttpResponse response = RequestHelper.getResponse(request);
        return this.parse(request, response);
    }

    private <T extends BaseResponse> void setHostAndSignature(BaseRequest<T> request) throws Exception {
        if (request == null) {
            throw new ClientException("SDK.Request", "Request can not be null!");
        }
        if (this.profile == null) {
            throw new ClientException("SDK.Profile", "Profile can not be null!");
        }
        if (this.profile.getAccessKeyId().isEmpty() || this.profile.getSecretKey().isEmpty()) {
            throw new ClientException("SDK.Profile", "Invalid accessKeyId/accessKey");
        }
        HashMap<String, String> dics = new HashMap<>();

        dics.put("x-ca-accesskeyid", this.profile.getAccessKeyId());
        dics.put("x-ca-version", this.profile.getVersion());

        for (String key : dics.keySet()) {
            request.setHeader(key, dics.get(key));
        }

        request.setScheme(this.scheme);
        request.setHost(this.host);

        if (this.profile.getGlobalLanguage() != null) {
            request.setLanguage(this.profile.getGlobalLanguage());
        }
    }

    private <T extends BaseResponse> T parse(BaseRequest<T> request, HttpResponse httpResponse) throws Exception {
        String responseString = httpResponse.getResponseString();
        T t = getInstance(request.getResponseClass());
        if (responseString == null || responseString.isEmpty()) {
            t.setHeaders(httpResponse.getHeaders());
            t.setStatusCode(httpResponse.getStatusCode());
            return t;
        }
        if (t.getResponseType() == ResponseType.Json) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            T _t = mapper.readValue(httpResponse.getResponseString(), request.getResponseClass());
            _t.setHeaders(httpResponse.getHeaders());
            _t.setStatusCode(httpResponse.getStatusCode());
            _t.setResponseBase64String(Base64.getEncoder().encodeToString(httpResponse.getResponseBytes()));

            if (httpResponse.getStatusCode() >= 500) {
                throw new ServerException(String.format("%s", _t.getErrorCode()),
                        String.format("%s%s%s",
                                _t.getErrorMsg() == null || _t.getErrorMsg().isEmpty() ? "" : _t.getErrorMsg(),
                                _t.getMessage() == null || _t.getMessage().isEmpty() ? "" : _t.getMessage(),
                                _t.getMsg() == null || _t.getMsg().isEmpty() ? "" : _t.getMsg()));
            } else if (httpResponse.getStatusCode() >= 400) {
                throw new ClientException(String.format("%s", _t.getErrorCode()),
                        String.format("%s%s%s",
                                _t.getErrorMsg() == null || _t.getErrorMsg().isEmpty() ? "" : _t.getErrorMsg(),
                                _t.getMessage() == null || _t.getMessage().isEmpty() ? "" : _t.getMessage(),
                                _t.getMsg() == null || _t.getMsg().isEmpty() ? "" : _t.getMsg()),
                        _t.getRequestId());
            }

            return _t;
        }
        return t;
    }

    private <T extends BaseResponse> T getInstance(Class<T> clasz) throws ClientException {
        try {
            return clasz.newInstance();
        } catch (Exception e) {
            throw new ClientException("SDK.InvalidResponseClass", "Unable to allocate " + clasz.getName() + " class");
        }
    }
}
