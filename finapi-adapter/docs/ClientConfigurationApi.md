# ClientConfigurationApi

All URIs are relative to *https://localhost/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**editClientConfiguration**](ClientConfigurationApi.md#editClientConfiguration) | **PATCH** /api/v1/clientConfiguration | Edit client configuration
[**getClientConfiguration**](ClientConfigurationApi.md#getClientConfiguration) | **GET** /api/v1/clientConfiguration | Get client configuration


<a name="editClientConfiguration"></a>
# **editClientConfiguration**
> InlineResponse20011 editClientConfiguration(body)

Edit client configuration

Edit your client&#39;s configuration. Must pass your global (i.e. client) access_token.&lt;br/&gt;&lt;br/&gt; &lt;b&gt;NOTE&lt;/b&gt;: When token validity periods are changed, this only applies to newly requested tokens, and does not change the expiration time of already existing tokens.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ClientConfigurationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: finapi_auth
OAuth finapi_auth = (OAuth) defaultClient.getAuthentication("finapi_auth");
finapi_auth.setAccessToken("YOUR ACCESS TOKEN");

ClientConfigurationApi apiInstance = new ClientConfigurationApi();
Body7 body = new Body7(); // Body7 | Client configuration parameters
try {
    InlineResponse20011 result = apiInstance.editClientConfiguration(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientConfigurationApi#editClientConfiguration");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Body7**](Body7.md)| Client configuration parameters | [optional]

### Return type

[**InlineResponse20011**](InlineResponse20011.md)

### Authorization

[finapi_auth](../README.md#finapi_auth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getClientConfiguration"></a>
# **getClientConfiguration**
> InlineResponse20011 getClientConfiguration()

Get client configuration

Get your client&#39;s configuration. Must pass your global (i.e. client) access_token.

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ClientConfigurationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: finapi_auth
OAuth finapi_auth = (OAuth) defaultClient.getAuthentication("finapi_auth");
finapi_auth.setAccessToken("YOUR ACCESS TOKEN");

ClientConfigurationApi apiInstance = new ClientConfigurationApi();
try {
    InlineResponse20011 result = apiInstance.getClientConfiguration();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ClientConfigurationApi#getClientConfiguration");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**InlineResponse20011**](InlineResponse20011.md)

### Authorization

[finapi_auth](../README.md#finapi_auth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

