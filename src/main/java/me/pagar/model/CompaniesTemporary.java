package me.pagar.model;


import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import me.pagar.util.JSONUtils;

import javax.ws.rs.HttpMethod;
import java.util.Map;
import java.util.HashMap;

public class CompaniesTemporary extends PagarMeModel<Integer>{

    @Expose
    @SerializedName("api_key")
    private Map<String, Object> apiKey;

    public String getTemporaryCompanyApiKey(String apiVersion) {
        try {
            final PagarMeRequest request = new PagarMeRequest(HttpMethod.POST, "/companies/temporary");
            request.setParameters(buildApiVersionParameter(apiVersion));
            CompaniesTemporary company = JSONUtils.getAsObject((JsonObject) request.execute(), CompaniesTemporary.class);

            return company.apiKey.get("test").toString();

        } catch (PagarMeException exception) {
            throw new UnsupportedOperationException(exception);
        }
    }

    public Map<String, Object> buildApiVersionParameter(String apiVersion) {
        Map<String, Object> version = new HashMap<String, Object>();
        Map<String, Object> type = new HashMap<String, Object>();
        type.put("test", apiVersion);
        type.put("live", apiVersion);

        version.put("api_version", type);

        return version;
    }
}