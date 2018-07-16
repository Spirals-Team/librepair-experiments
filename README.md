# factern-java-client

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>com.factern</groupId>
    <artifactId>factern-java-client</artifactId>
    <version>1.0.21</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.factern:factern-java-client:1.0.21"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/factern-java-client-1.0.21.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import com.factern.*;
import com.factern.auth.*;
import com.factern.model.*;
import com.factern.api.FactsApi;

import java.io.File;
import java.util.*;

public class FactsApiExample {

    public static void main(String[] args) {
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
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://api.factern.com/v2*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*FactsApi* | [**addMember**](docs/FactsApi.md#addMember) | **POST** /createmember | Create Member
*FactsApi* | [**bid**](docs/FactsApi.md#bid) | **POST** /createbid | Create Bid
*FactsApi* | [**createAlias**](docs/FactsApi.md#createAlias) | **POST** /createalias | Create Alias
*FactsApi* | [**createApplication**](docs/FactsApi.md#createApplication) | **POST** /createapplication | Create Application
*FactsApi* | [**createDomain**](docs/FactsApi.md#createDomain) | **POST** /createdomain | Create Domain
*FactsApi* | [**createEntity**](docs/FactsApi.md#createEntity) | **POST** /createentity | Create Entity
*FactsApi* | [**createField**](docs/FactsApi.md#createField) | **POST** /createfield | Create Field
*FactsApi* | [**createFilter**](docs/FactsApi.md#createFilter) | **POST** /createfilter | Create Filter
*FactsApi* | [**createGroup**](docs/FactsApi.md#createGroup) | **POST** /creategroup | Create Group
*FactsApi* | [**createInformation**](docs/FactsApi.md#createInformation) | **POST** /createinformation | Create Information
*FactsApi* | [**createInterface**](docs/FactsApi.md#createInterface) | **POST** /createinterface | Create Interface
*FactsApi* | [**createLabelList**](docs/FactsApi.md#createLabelList) | **POST** /createlabellist | Create Label List
*FactsApi* | [**createLogin**](docs/FactsApi.md#createLogin) | **POST** /createlogin | Create Login
*FactsApi* | [**createMirror**](docs/FactsApi.md#createMirror) | **POST** /createmirror | Create Mirror
*FactsApi* | [**createPrice**](docs/FactsApi.md#createPrice) | **POST** /createprice | Create Price
*FactsApi* | [**createScope**](docs/FactsApi.md#createScope) | **POST** /createscope | Create Scope
*FactsApi* | [**createStatement**](docs/FactsApi.md#createStatement) | **POST** /createstatement | Create Statement
*FactsApi* | [**createTemplate**](docs/FactsApi.md#createTemplate) | **POST** /createtemplate | Create Template
*FactsApi* | [**delete**](docs/FactsApi.md#delete) | **POST** /delete | Deleting
*FactsApi* | [**deleteNode**](docs/FactsApi.md#deleteNode) | **POST** /deletenode | Delete Node
*FactsApi* | [**describe**](docs/FactsApi.md#describe) | **POST** /describe | Describe
*FactsApi* | [**history**](docs/FactsApi.md#history) | **POST** /history | History
*FactsApi* | [**label**](docs/FactsApi.md#label) | **POST** /label | Label a Node
*FactsApi* | [**obliterate**](docs/FactsApi.md#obliterate) | **POST** /obliterate | Obliterating Information Nodes
*FactsApi* | [**permission**](docs/FactsApi.md#permission) | **POST** /permission | Create Permission
*FactsApi* | [**read**](docs/FactsApi.md#read) | **POST** /read | Reading
*FactsApi* | [**replaceInformation**](docs/FactsApi.md#replaceInformation) | **POST** /replaceinformation | Replace
*FactsApi* | [**requestPermission**](docs/FactsApi.md#requestPermission) | **POST** /requestpermission | Request Permission
*FactsApi* | [**resetLogin**](docs/FactsApi.md#resetLogin) | **POST** /resetlogin | Changing Login Password
*FactsApi* | [**searchAlias**](docs/FactsApi.md#searchAlias) | **POST** /searchalias | Search For Alias
*FactsApi* | [**searchEntity**](docs/FactsApi.md#searchEntity) | **POST** /searchentity | Search For Entity
*FactsApi* | [**settleAccount**](docs/FactsApi.md#settleAccount) | **POST** /settleaccount | Settle Account
*FactsApi* | [**updateApplication**](docs/FactsApi.md#updateApplication) | **POST** /updateapplication | Resetting Application Secret
*FactsApi* | [**updateStatus**](docs/FactsApi.md#updateStatus) | **POST** /updatestatus | Enabling/Disabling Nodes
*FactsApi* | [**watch**](docs/FactsApi.md#watch) | **POST** /watch | Create Watch Trigger
*FactsApi* | [**write**](docs/FactsApi.md#write) | **POST** /write | Writing by Template


## Documentation for Models

 - [Account](docs/Account.md)
 - [Agent](docs/Agent.md)
 - [AliasNode](docs/AliasNode.md)
 - [ApiEndpoint](docs/ApiEndpoint.md)
 - [ApplicationNode](docs/ApplicationNode.md)
 - [BaseRequest](docs/BaseRequest.md)
 - [BaseResponse](docs/BaseResponse.md)
 - [BidNode](docs/BidNode.md)
 - [Cost](docs/Cost.md)
 - [DescribeResponse](docs/DescribeResponse.md)
 - [DomainNode](docs/DomainNode.md)
 - [EntityListResponse](docs/EntityListResponse.md)
 - [EntityNode](docs/EntityNode.md)
 - [FactCount](docs/FactCount.md)
 - [FieldNode](docs/FieldNode.md)
 - [FieldStoreValues](docs/FieldStoreValues.md)
 - [FilterNode](docs/FilterNode.md)
 - [FilterStatement](docs/FilterStatement.md)
 - [GasCost](docs/GasCost.md)
 - [GroupNode](docs/GroupNode.md)
 - [HttpHeader](docs/HttpHeader.md)
 - [InformationListResponse](docs/InformationListResponse.md)
 - [InformationNode](docs/InformationNode.md)
 - [InterfaceNode](docs/InterfaceNode.md)
 - [LabelListMemberNode](docs/LabelListMemberNode.md)
 - [LabelListNode](docs/LabelListNode.md)
 - [LabelStatement](docs/LabelStatement.md)
 - [ListCriteria](docs/ListCriteria.md)
 - [LoginNode](docs/LoginNode.md)
 - [MemberNode](docs/MemberNode.md)
 - [MirrorNode](docs/MirrorNode.md)
 - [NamedNode](docs/NamedNode.md)
 - [NodeListing](docs/NodeListing.md)
 - [PermissionAction](docs/PermissionAction.md)
 - [PermissionEffect](docs/PermissionEffect.md)
 - [PermissionNode](docs/PermissionNode.md)
 - [PermissionPolicyDocument](docs/PermissionPolicyDocument.md)
 - [PriceDetails](docs/PriceDetails.md)
 - [PriceNode](docs/PriceNode.md)
 - [ReadItem](docs/ReadItem.md)
 - [ReadResponse](docs/ReadResponse.md)
 - [ReadStatusItem](docs/ReadStatusItem.md)
 - [ScopeNode](docs/ScopeNode.md)
 - [SearchAliasResponse](docs/SearchAliasResponse.md)
 - [SettleAccountResponse](docs/SettleAccountResponse.md)
 - [StandardNode](docs/StandardNode.md)
 - [StandardNodeResponse](docs/StandardNodeResponse.md)
 - [StatementStatement](docs/StatementStatement.md)
 - [Summary](docs/Summary.md)
 - [TemplateNode](docs/TemplateNode.md)
 - [TokenPayment](docs/TokenPayment.md)
 - [TransformElement](docs/TransformElement.md)
 - [WatchEventNode](docs/WatchEventNode.md)
 - [WatchNode](docs/WatchNode.md)
 - [WriteItem](docs/WriteItem.md)
 - [WriteResponse](docs/WriteResponse.md)
 - [AddLabelRequest](docs/AddLabelRequest.md)
 - [AddLabelResponse](docs/AddLabelResponse.md)
 - [AddStatementRequest](docs/AddStatementRequest.md)
 - [AddStatementResponse](docs/AddStatementResponse.md)
 - [Alias](docs/Alias.md)
 - [Application](docs/Application.md)
 - [Bid](docs/Bid.md)
 - [CreateAliasRequest](docs/CreateAliasRequest.md)
 - [CreateAliasResponse](docs/CreateAliasResponse.md)
 - [CreateApplicationResponse](docs/CreateApplicationResponse.md)
 - [CreateBidRequest](docs/CreateBidRequest.md)
 - [CreateBidResponse](docs/CreateBidResponse.md)
 - [CreateChildRequest](docs/CreateChildRequest.md)
 - [CreateDomainResponse](docs/CreateDomainResponse.md)
 - [CreateEntityResponse](docs/CreateEntityResponse.md)
 - [CreateFieldResponse](docs/CreateFieldResponse.md)
 - [CreateFilterResponse](docs/CreateFilterResponse.md)
 - [CreateGroupResponse](docs/CreateGroupResponse.md)
 - [CreateInformationResponse](docs/CreateInformationResponse.md)
 - [CreateInterfaceResponse](docs/CreateInterfaceResponse.md)
 - [CreateLabelListResponse](docs/CreateLabelListResponse.md)
 - [CreateLoginRequest](docs/CreateLoginRequest.md)
 - [CreateLoginResponse](docs/CreateLoginResponse.md)
 - [CreateMemberResponse](docs/CreateMemberResponse.md)
 - [CreateMirrorRequest](docs/CreateMirrorRequest.md)
 - [CreateMirrorResponse](docs/CreateMirrorResponse.md)
 - [CreatePermissionRequest](docs/CreatePermissionRequest.md)
 - [CreatePermissionResponse](docs/CreatePermissionResponse.md)
 - [CreatePriceRequest](docs/CreatePriceRequest.md)
 - [CreatePriceResponse](docs/CreatePriceResponse.md)
 - [CreateScopeResponse](docs/CreateScopeResponse.md)
 - [CreateTemplateResponse](docs/CreateTemplateResponse.md)
 - [CreateWatchRequest](docs/CreateWatchRequest.md)
 - [CreateWatchResponse](docs/CreateWatchResponse.md)
 - [DeleteRequest](docs/DeleteRequest.md)
 - [DescribeRequest](docs/DescribeRequest.md)
 - [Domain](docs/Domain.md)
 - [Entity](docs/Entity.md)
 - [Field](docs/Field.md)
 - [Filter](docs/Filter.md)
 - [Group](docs/Group.md)
 - [Information](docs/Information.md)
 - [Label](docs/Label.md)
 - [LabelList](docs/LabelList.md)
 - [LabelListMember](docs/LabelListMember.md)
 - [Login](docs/Login.md)
 - [Member](docs/Member.md)
 - [Mirror](docs/Mirror.md)
 - [ModelInterface](docs/ModelInterface.md)
 - [NodeIdRequest](docs/NodeIdRequest.md)
 - [Permission](docs/Permission.md)
 - [Price](docs/Price.md)
 - [ReadRequest](docs/ReadRequest.md)
 - [ReplaceFieldRequest](docs/ReplaceFieldRequest.md)
 - [ResetLoginCredentialsRequest](docs/ResetLoginCredentialsRequest.md)
 - [ResetLoginResponse](docs/ResetLoginResponse.md)
 - [Scope](docs/Scope.md)
 - [SearchAliasRequest](docs/SearchAliasRequest.md)
 - [SearchEntityRequest](docs/SearchEntityRequest.md)
 - [SettleAccountRequest](docs/SettleAccountRequest.md)
 - [Statement](docs/Statement.md)
 - [Template](docs/Template.md)
 - [UpdateApplicationRequest](docs/UpdateApplicationRequest.md)
 - [UpdateApplicationResponse](docs/UpdateApplicationResponse.md)
 - [Watch](docs/Watch.md)
 - [WatchEvent](docs/WatchEvent.md)
 - [WriteRequest](docs/WriteRequest.md)
 - [CreateInformationRequest](docs/CreateInformationRequest.md)
 - [CreateMemberRequest](docs/CreateMemberRequest.md)
 - [CreateNamedRequest](docs/CreateNamedRequest.md)
 - [UpdateStatusRequest](docs/UpdateStatusRequest.md)
 - [CreateApplicationRequest](docs/CreateApplicationRequest.md)
 - [CreateDomainRequest](docs/CreateDomainRequest.md)
 - [CreateFieldRequest](docs/CreateFieldRequest.md)
 - [CreateFilterRequest](docs/CreateFilterRequest.md)
 - [CreateGroupRequest](docs/CreateGroupRequest.md)
 - [CreateInDomainRequest](docs/CreateInDomainRequest.md)
 - [CreateInterfaceRequest](docs/CreateInterfaceRequest.md)
 - [CreateLabelListRequest](docs/CreateLabelListRequest.md)
 - [CreateScopeRequest](docs/CreateScopeRequest.md)
 - [CreateTemplateRequest](docs/CreateTemplateRequest.md)


## Documentation for Authorization

Authentication schemes defined for the API:
### OAuth2

- **Type**: OAuth
- **Flow**: accessCode
- **Authorization URL**: https://factern-test.eu.auth0.com/oauth/token
- **Scopes**: 
  - profile: Grants read and write access to administrative information
  - openid: Grants read and write access to administrative information
  - email: Grants read and write access to administrative information
  - phone: Grants write access
  - address: Grants read access


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author



