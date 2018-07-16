# FactsApi

All URIs are relative to *https://api.factern.com/v2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addMember**](FactsApi.md#addMember) | **POST** /createmember | Create Member
[**bid**](FactsApi.md#bid) | **POST** /createbid | Create Bid
[**createAlias**](FactsApi.md#createAlias) | **POST** /createalias | Create Alias
[**createApplication**](FactsApi.md#createApplication) | **POST** /createapplication | Create Application
[**createDomain**](FactsApi.md#createDomain) | **POST** /createdomain | Create Domain
[**createEntity**](FactsApi.md#createEntity) | **POST** /createentity | Create Entity
[**createField**](FactsApi.md#createField) | **POST** /createfield | Create Field
[**createFilter**](FactsApi.md#createFilter) | **POST** /createfilter | Create Filter
[**createGroup**](FactsApi.md#createGroup) | **POST** /creategroup | Create Group
[**createInformation**](FactsApi.md#createInformation) | **POST** /createinformation | Create Information
[**createInterface**](FactsApi.md#createInterface) | **POST** /createinterface | Create Interface
[**createLabelList**](FactsApi.md#createLabelList) | **POST** /createlabellist | Create Label List
[**createLogin**](FactsApi.md#createLogin) | **POST** /createlogin | Create Login
[**createMirror**](FactsApi.md#createMirror) | **POST** /createmirror | Create Mirror
[**createPrice**](FactsApi.md#createPrice) | **POST** /createprice | Create Price
[**createScope**](FactsApi.md#createScope) | **POST** /createscope | Create Scope
[**createStatement**](FactsApi.md#createStatement) | **POST** /createstatement | Create Statement
[**createTemplate**](FactsApi.md#createTemplate) | **POST** /createtemplate | Create Template
[**delete**](FactsApi.md#delete) | **POST** /delete | Deleting
[**deleteNode**](FactsApi.md#deleteNode) | **POST** /deletenode | Delete Node
[**describe**](FactsApi.md#describe) | **POST** /describe | Describe
[**history**](FactsApi.md#history) | **POST** /history | History
[**label**](FactsApi.md#label) | **POST** /label | Label a Node
[**obliterate**](FactsApi.md#obliterate) | **POST** /obliterate | Obliterating Information Nodes
[**permission**](FactsApi.md#permission) | **POST** /permission | Create Permission
[**read**](FactsApi.md#read) | **POST** /read | Reading
[**replaceInformation**](FactsApi.md#replaceInformation) | **POST** /replaceinformation | Replace
[**requestPermission**](FactsApi.md#requestPermission) | **POST** /requestpermission | Request Permission
[**resetLogin**](FactsApi.md#resetLogin) | **POST** /resetlogin | Changing Login Password
[**searchAlias**](FactsApi.md#searchAlias) | **POST** /searchalias | Search For Alias
[**searchEntity**](FactsApi.md#searchEntity) | **POST** /searchentity | Search For Entity
[**settleAccount**](FactsApi.md#settleAccount) | **POST** /settleaccount | Settle Account
[**updateApplication**](FactsApi.md#updateApplication) | **POST** /updateapplication | Resetting Application Secret
[**updateStatus**](FactsApi.md#updateStatus) | **POST** /updatestatus | Enabling/Disabling Nodes
[**watch**](FactsApi.md#watch) | **POST** /watch | Create Watch Trigger
[**write**](FactsApi.md#write) | **POST** /write | Writing by Template


<a name="addMember"></a>
# **addMember**
> CreateMemberResponse addMember(login, representing, body)

Create Member

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateMemberRequest body = new CreateMemberRequest(); // CreateMemberRequest | 
try {
    CreateMemberResponse result = apiInstance.addMember(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#addMember");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateMemberRequest**](CreateMemberRequest.md)|  | [optional]

### Return type

[**CreateMemberResponse**](CreateMemberResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="bid"></a>
# **bid**
> StandardNodeResponse bid(login, representing, body)

Create Bid

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateBidRequest body = new CreateBidRequest(); // CreateBidRequest | 
try {
    StandardNodeResponse result = apiInstance.bid(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#bid");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateBidRequest**](CreateBidRequest.md)|  | [optional]

### Return type

[**StandardNodeResponse**](StandardNodeResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createAlias"></a>
# **createAlias**
> CreateAliasResponse createAlias(login, representing, body)

Create Alias

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateAliasRequest body = new CreateAliasRequest(); // CreateAliasRequest | 
try {
    CreateAliasResponse result = apiInstance.createAlias(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createAlias");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateAliasRequest**](CreateAliasRequest.md)|  | [optional]

### Return type

[**CreateAliasResponse**](CreateAliasResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createApplication"></a>
# **createApplication**
> CreateApplicationResponse createApplication(login, representing, body)

Create Application

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateApplicationRequest body = new CreateApplicationRequest(); // CreateApplicationRequest | 
try {
    CreateApplicationResponse result = apiInstance.createApplication(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createApplication");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateApplicationRequest**](CreateApplicationRequest.md)|  | [optional]

### Return type

[**CreateApplicationResponse**](CreateApplicationResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createDomain"></a>
# **createDomain**
> CreateDomainResponse createDomain(login, representing, body)

Create Domain

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateDomainRequest body = new CreateDomainRequest(); // CreateDomainRequest | 
try {
    CreateDomainResponse result = apiInstance.createDomain(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createDomain");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateDomainRequest**](CreateDomainRequest.md)|  | [optional]

### Return type

[**CreateDomainResponse**](CreateDomainResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createEntity"></a>
# **createEntity**
> CreateEntityResponse createEntity(login, representing, body)

Create Entity

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateInDomainRequest body = new CreateInDomainRequest(); // CreateInDomainRequest | 
try {
    CreateEntityResponse result = apiInstance.createEntity(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createEntity");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateInDomainRequest**](CreateInDomainRequest.md)|  | [optional]

### Return type

[**CreateEntityResponse**](CreateEntityResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createField"></a>
# **createField**
> CreateFieldResponse createField(login, representing, body)

Create Field

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateFieldRequest body = new CreateFieldRequest(); // CreateFieldRequest | 
try {
    CreateFieldResponse result = apiInstance.createField(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createField");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateFieldRequest**](CreateFieldRequest.md)|  | [optional]

### Return type

[**CreateFieldResponse**](CreateFieldResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createFilter"></a>
# **createFilter**
> CreateFilterResponse createFilter(login, representing, body)

Create Filter

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateFilterRequest body = new CreateFilterRequest(); // CreateFilterRequest | 
try {
    CreateFilterResponse result = apiInstance.createFilter(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createFilter");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateFilterRequest**](CreateFilterRequest.md)|  | [optional]

### Return type

[**CreateFilterResponse**](CreateFilterResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createGroup"></a>
# **createGroup**
> CreateGroupResponse createGroup(login, representing, body)

Create Group

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateGroupRequest body = new CreateGroupRequest(); // CreateGroupRequest | 
try {
    CreateGroupResponse result = apiInstance.createGroup(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createGroup");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateGroupRequest**](CreateGroupRequest.md)|  | [optional]

### Return type

[**CreateGroupResponse**](CreateGroupResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createInformation"></a>
# **createInformation**
> CreateInformationResponse createInformation(login, representing, body)

Create Information

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateInformationRequest body = new CreateInformationRequest(); // CreateInformationRequest | 
try {
    CreateInformationResponse result = apiInstance.createInformation(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createInformation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateInformationRequest**](CreateInformationRequest.md)|  | [optional]

### Return type

[**CreateInformationResponse**](CreateInformationResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createInterface"></a>
# **createInterface**
> CreateInterfaceResponse createInterface(login, representing, body)

Create Interface

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateInterfaceRequest body = new CreateInterfaceRequest(); // CreateInterfaceRequest | 
try {
    CreateInterfaceResponse result = apiInstance.createInterface(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createInterface");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateInterfaceRequest**](CreateInterfaceRequest.md)|  | [optional]

### Return type

[**CreateInterfaceResponse**](CreateInterfaceResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createLabelList"></a>
# **createLabelList**
> CreateLabelListResponse createLabelList(login, representing, body)

Create Label List

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateLabelListRequest body = new CreateLabelListRequest(); // CreateLabelListRequest | 
try {
    CreateLabelListResponse result = apiInstance.createLabelList(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createLabelList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateLabelListRequest**](CreateLabelListRequest.md)|  | [optional]

### Return type

[**CreateLabelListResponse**](CreateLabelListResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createLogin"></a>
# **createLogin**
> CreateLoginResponse createLogin(login, representing, body)

Create Login

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateLoginRequest body = new CreateLoginRequest(); // CreateLoginRequest | 
try {
    CreateLoginResponse result = apiInstance.createLogin(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createLogin");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateLoginRequest**](CreateLoginRequest.md)|  | [optional]

### Return type

[**CreateLoginResponse**](CreateLoginResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createMirror"></a>
# **createMirror**
> CreateMirrorResponse createMirror(login, representing, body)

Create Mirror

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateMirrorRequest body = new CreateMirrorRequest(); // CreateMirrorRequest | 
try {
    CreateMirrorResponse result = apiInstance.createMirror(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createMirror");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateMirrorRequest**](CreateMirrorRequest.md)|  | [optional]

### Return type

[**CreateMirrorResponse**](CreateMirrorResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createPrice"></a>
# **createPrice**
> CreatePriceResponse createPrice(login, representing, body)

Create Price

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreatePriceRequest body = new CreatePriceRequest(); // CreatePriceRequest | 
try {
    CreatePriceResponse result = apiInstance.createPrice(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createPrice");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreatePriceRequest**](CreatePriceRequest.md)|  | [optional]

### Return type

[**CreatePriceResponse**](CreatePriceResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createScope"></a>
# **createScope**
> CreateScopeResponse createScope(login, representing, body)

Create Scope

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateScopeRequest body = new CreateScopeRequest(); // CreateScopeRequest | 
try {
    CreateScopeResponse result = apiInstance.createScope(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createScope");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateScopeRequest**](CreateScopeRequest.md)|  | [optional]

### Return type

[**CreateScopeResponse**](CreateScopeResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createStatement"></a>
# **createStatement**
> AddStatementResponse createStatement(login, representing, body)

Create Statement

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
AddStatementRequest body = new AddStatementRequest(); // AddStatementRequest | 
try {
    AddStatementResponse result = apiInstance.createStatement(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createStatement");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**AddStatementRequest**](AddStatementRequest.md)|  | [optional]

### Return type

[**AddStatementResponse**](AddStatementResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createTemplate"></a>
# **createTemplate**
> CreateTemplateResponse createTemplate(login, representing, body)

Create Template

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateTemplateRequest body = new CreateTemplateRequest(); // CreateTemplateRequest | 
try {
    CreateTemplateResponse result = apiInstance.createTemplate(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#createTemplate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateTemplateRequest**](CreateTemplateRequest.md)|  | [optional]

### Return type

[**CreateTemplateResponse**](CreateTemplateResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="delete"></a>
# **delete**
> ReadResponse delete(login, representing, body)

Deleting

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
DeleteRequest body = new DeleteRequest(); // DeleteRequest | 
try {
    ReadResponse result = apiInstance.delete(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#delete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**DeleteRequest**](DeleteRequest.md)|  | [optional]

### Return type

[**ReadResponse**](ReadResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="deleteNode"></a>
# **deleteNode**
> StandardNodeResponse deleteNode(login, representing, body)

Delete Node

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
NodeIdRequest body = new NodeIdRequest(); // NodeIdRequest | 
try {
    StandardNodeResponse result = apiInstance.deleteNode(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#deleteNode");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**NodeIdRequest**](NodeIdRequest.md)|  | [optional]

### Return type

[**StandardNodeResponse**](StandardNodeResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="describe"></a>
# **describe**
> DescribeResponse describe(login, representing, body)

Describe

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
DescribeRequest body = new DescribeRequest(); // DescribeRequest | 
try {
    DescribeResponse result = apiInstance.describe(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#describe");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**DescribeRequest**](DescribeRequest.md)|  | [optional]

### Return type

[**DescribeResponse**](DescribeResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="history"></a>
# **history**
> InformationListResponse history(login, representing, body)

History

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
NodeIdRequest body = new NodeIdRequest(); // NodeIdRequest | 
try {
    InformationListResponse result = apiInstance.history(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#history");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**NodeIdRequest**](NodeIdRequest.md)|  | [optional]

### Return type

[**InformationListResponse**](InformationListResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="label"></a>
# **label**
> AddLabelResponse label(login, representing, body)

Label a Node

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
AddLabelRequest body = new AddLabelRequest(); // AddLabelRequest | 
try {
    AddLabelResponse result = apiInstance.label(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#label");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**AddLabelRequest**](AddLabelRequest.md)|  | [optional]

### Return type

[**AddLabelResponse**](AddLabelResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="obliterate"></a>
# **obliterate**
> StandardNodeResponse obliterate(login, representing, body)

Obliterating Information Nodes

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
NodeIdRequest body = new NodeIdRequest(); // NodeIdRequest | 
try {
    StandardNodeResponse result = apiInstance.obliterate(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#obliterate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**NodeIdRequest**](NodeIdRequest.md)|  | [optional]

### Return type

[**StandardNodeResponse**](StandardNodeResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="permission"></a>
# **permission**
> CreatePermissionResponse permission(login, representing, body)

Create Permission

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreatePermissionRequest body = new CreatePermissionRequest(); // CreatePermissionRequest | 
try {
    CreatePermissionResponse result = apiInstance.permission(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#permission");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreatePermissionRequest**](CreatePermissionRequest.md)|  | [optional]

### Return type

[**CreatePermissionResponse**](CreatePermissionResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="read"></a>
# **read**
> ReadResponse read(login, representing, body)

Reading

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
ReadRequest body = new ReadRequest(); // ReadRequest | 
try {
    ReadResponse result = apiInstance.read(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#read");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**ReadRequest**](ReadRequest.md)|  | [optional]

### Return type

[**ReadResponse**](ReadResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="replaceInformation"></a>
# **replaceInformation**
> DescribeResponse replaceInformation(login, representing, body)

Replace

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
ReplaceFieldRequest body = new ReplaceFieldRequest(); // ReplaceFieldRequest | 
try {
    DescribeResponse result = apiInstance.replaceInformation(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#replaceInformation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**ReplaceFieldRequest**](ReplaceFieldRequest.md)|  | [optional]

### Return type

[**DescribeResponse**](DescribeResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="requestPermission"></a>
# **requestPermission**
> StandardNodeResponse requestPermission(login, representing, body)

Request Permission

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
NodeIdRequest body = new NodeIdRequest(); // NodeIdRequest | 
try {
    StandardNodeResponse result = apiInstance.requestPermission(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#requestPermission");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**NodeIdRequest**](NodeIdRequest.md)|  | [optional]

### Return type

[**StandardNodeResponse**](StandardNodeResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="resetLogin"></a>
# **resetLogin**
> ResetLoginResponse resetLogin(login, representing, body)

Changing Login Password

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
ResetLoginCredentialsRequest body = new ResetLoginCredentialsRequest(); // ResetLoginCredentialsRequest | 
try {
    ResetLoginResponse result = apiInstance.resetLogin(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#resetLogin");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**ResetLoginCredentialsRequest**](ResetLoginCredentialsRequest.md)|  | [optional]

### Return type

[**ResetLoginResponse**](ResetLoginResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="searchAlias"></a>
# **searchAlias**
> SearchAliasResponse searchAlias(login, representing, body)

Search For Alias

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
SearchAliasRequest body = new SearchAliasRequest(); // SearchAliasRequest | 
try {
    SearchAliasResponse result = apiInstance.searchAlias(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#searchAlias");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**SearchAliasRequest**](SearchAliasRequest.md)|  | [optional]

### Return type

[**SearchAliasResponse**](SearchAliasResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="searchEntity"></a>
# **searchEntity**
> EntityListResponse searchEntity(login, representing, body)

Search For Entity

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
SearchEntityRequest body = new SearchEntityRequest(); // SearchEntityRequest | 
try {
    EntityListResponse result = apiInstance.searchEntity(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#searchEntity");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**SearchEntityRequest**](SearchEntityRequest.md)|  | [optional]

### Return type

[**EntityListResponse**](EntityListResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="settleAccount"></a>
# **settleAccount**
> SettleAccountResponse settleAccount(login, representing, body)

Settle Account

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
SettleAccountRequest body = new SettleAccountRequest(); // SettleAccountRequest | 
try {
    SettleAccountResponse result = apiInstance.settleAccount(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#settleAccount");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**SettleAccountRequest**](SettleAccountRequest.md)|  | [optional]

### Return type

[**SettleAccountResponse**](SettleAccountResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateApplication"></a>
# **updateApplication**
> UpdateApplicationResponse updateApplication(login, representing, body)

Resetting Application Secret

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
UpdateApplicationRequest body = new UpdateApplicationRequest(); // UpdateApplicationRequest | 
try {
    UpdateApplicationResponse result = apiInstance.updateApplication(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#updateApplication");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**UpdateApplicationRequest**](UpdateApplicationRequest.md)|  | [optional]

### Return type

[**UpdateApplicationResponse**](UpdateApplicationResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateStatus"></a>
# **updateStatus**
> StandardNodeResponse updateStatus(login, representing, body)

Enabling/Disabling Nodes

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
UpdateStatusRequest body = new UpdateStatusRequest(); // UpdateStatusRequest | 
try {
    StandardNodeResponse result = apiInstance.updateStatus(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#updateStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**UpdateStatusRequest**](UpdateStatusRequest.md)|  | [optional]

### Return type

[**StandardNodeResponse**](StandardNodeResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="watch"></a>
# **watch**
> CreateWatchResponse watch(login, representing, body)

Create Watch Trigger

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
CreateWatchRequest body = new CreateWatchRequest(); // CreateWatchRequest | 
try {
    CreateWatchResponse result = apiInstance.watch(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#watch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**CreateWatchRequest**](CreateWatchRequest.md)|  | [optional]

### Return type

[**CreateWatchResponse**](CreateWatchResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="write"></a>
# **write**
> WriteResponse write(login, representing, body)

Writing by Template

### Example
```java
// Import classes:
//import com.factern.ApiClient;
//import com.factern.ApiException;
//import com.factern.Configuration;
//import com.factern.auth.*;
//import com.factern.api.FactsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: OAuth2
OAuth OAuth2 = (OAuth) defaultClient.getAuthentication("OAuth2");
OAuth2.setAccessToken("YOUR ACCESS TOKEN");

FactsApi apiInstance = new FactsApi();
String login = "login_example"; // String | 
String representing = "representing_example"; // String | 
WriteRequest body = new WriteRequest(); // WriteRequest | 
try {
    WriteResponse result = apiInstance.write(login, representing, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling FactsApi#write");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **login** | **String**|  | [optional]
 **representing** | **String**|  | [optional]
 **body** | [**WriteRequest**](WriteRequest.md)|  | [optional]

### Return type

[**WriteResponse**](WriteResponse.md)

### Authorization

[OAuth2](../README.md#OAuth2)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

