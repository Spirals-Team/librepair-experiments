package me.pagar;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import me.pagar.converter.JSonConverter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.StringEntity;
import java.io.IOException;
import org.json.JSONObject;

public class PagarMeRequest {
    
    private static final String API_ENDPOINT =  "https://api.pagar.me/1";
    private static final String USER_AGENT = "Mozilla/5.0";
    
    @SerializedName("api_key")
    private String apiKey; 
    
    private String endPoint;
    private String method;
    private List<Map<String,String>> parameters;
    private JSONObject documentObj;
    private String responseString;
    private PagarMeQueryResponse pagarMeResponse;
    private int responseCode;

    @SerializedName("date_created")
    protected String dateCreated;
    
    public PagarMeRequest() {
        parameters = new ArrayList<Map<String,String>>();
    }
    
    /**
     * 
     * @param key
     * @param value 
     */
    public void setParameter(String key,String value) {
        parameters.add(ImmutableMap.of(key,value));
    }
    
    /**
     * 
     * @param objectProperties 
     */
    public void setParameters(Map<String,Object> objectProperties) {
        for (String key : objectProperties.keySet()) {
            setParameter(key, String.valueOf(objectProperties.get(key)));
        }
    }
    
    /**
     * 
     * @param method 
     */
    public void setMethod(String method) {
        this.method = method;
    }
    
    /**
     * 
     * @param endPoint 
     */
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
    
    /**
     * 
     * @return 
     */
    public String getEndPoint() {
        return this.endPoint;
    }
    
    /**
     * 
     * @return 
     */
    public String getMethod() {
        return this.method;
    }
    
    /**
     * 
     * @param apiKey 
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
    /**
     * 
     * @return 
     */
    public String getApiKey() {
        return this.apiKey;
    }
    
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
    
    public int getResponseCode() {
        return this.responseCode;
    }
    
    /**
     * 
     * @return 
     */
    public PagarMeQueryResponse charge() {
        
        String responseString = this.sendRequest(JSonConverter.objectToMap(this));
        
        try {
            pagarMeResponse = new PagarMeQueryResponse();
            pagarMeResponse.setStatus(this.getResponseCode());
            pagarMeResponse.setData(responseString);
            return pagarMeResponse; 
        } catch(Exception e) {
            return null;
        }
    }
    
    /**
     * 
     * @param params
     * @return 
     */
    public String sendRequest(Map params) {
        
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(this.getRequestUrl());
        httpPost.setHeader("User-Agent", USER_AGENT);
        httpPost.setHeader("Content-type", "application/json");
      
        this.documentObj = new JSONObject(params);
        httpPost.setEntity(new StringEntity(this.documentObj.toString(), "UTF-8"));

        try {
            
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
           
            BufferedReader reader = new BufferedReader(new InputStreamReader(
            httpResponse.getEntity().getContent()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            
            reader.close();
            httpClient.close();
            
            this.setResponseCode(httpResponse.getStatusLine().getStatusCode());
            
            return response.toString();
            
        } catch(IOException ex){
            return null;
        }
    }
    
    /**
     * 
     * @return 
     */
    private String getRequestUrl() {
        return API_ENDPOINT + this.getEndPoint();
    }
    
}
