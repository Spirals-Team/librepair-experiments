
# ApiEndpoint

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**body** | **String** |  |  [optional]
**url** | **String** |  | 
**responseTransform** | [**List&lt;TransformElement&gt;**](TransformElement.md) |  |  [optional]
**headers** | [**List&lt;HttpHeader&gt;**](HttpHeader.md) |  |  [optional]
**type** | [**TypeEnum**](#TypeEnum) |  |  [optional]
**method** | [**MethodEnum**](#MethodEnum) |  |  [optional]


<a name="TypeEnum"></a>
## Enum: TypeEnum
Name | Value
---- | -----
DIRECT | &quot;Direct&quot;
INDIRECT | &quot;Indirect&quot;


<a name="MethodEnum"></a>
## Enum: MethodEnum
Name | Value
---- | -----
GET | &quot;GET&quot;
POST | &quot;POST&quot;
PUT | &quot;PUT&quot;



