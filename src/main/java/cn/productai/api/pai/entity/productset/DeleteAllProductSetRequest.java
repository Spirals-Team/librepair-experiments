package cn.productai.api.pai.entity.productset;

import cn.productai.api.core.attribute.ParaSignAttribute;
import cn.productai.api.core.base.BaseRequest;
import cn.productai.api.core.enums.HttpMethod;
import cn.productai.api.core.enums.ServiceTypeId;

public class DeleteAllProductSetRequest extends BaseRequest<DeleteAllProductSetResponse> {

    @ParaSignAttribute(Name = "token")
    public String token;

    @ParaSignAttribute(Name = "b64_token")
    public String b64Token;

    public DeleteAllProductSetRequest(String token, String b64Token) {
        super();
        this.setToken(token);
        this.setB64Token(b64Token);
        this.setRequestMethod(HttpMethod.DELETE);
    }

    @Override
    public Class<DeleteAllProductSetResponse> getResponseClass() {
        return DeleteAllProductSetResponse.class;
    }

    @Override
    public String getApiUrl() {
        return String.format("%s://%s/product_sets/%s", this.getScheme(), this.getHost(), ServiceTypeId.PRODUCT_SET);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getB64Token() {
        return b64Token;
    }

    public void setB64Token(String b64Token) {
        this.b64Token = b64Token;
    }
}
