
# SearchEntityRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**includeSummary** | **Boolean** |  |  [optional]
**term** | **String** |  |  [optional]
**maxResults** | [**BigDecimal**](BigDecimal.md) |  |  [optional]
**restrictTo** | **String** |  |  [optional]
**operator** | [**OperatorEnum**](#OperatorEnum) |  |  [optional]
**query** | **Object** |  |  [optional]
**nextToken** | **String** |  |  [optional]
**fieldId** | **String** |  |  [optional]


<a name="OperatorEnum"></a>
## Enum: OperatorEnum
Name | Value
---- | -----
EQUALS | &quot;equals&quot;
STARTSWITH | &quot;startsWith&quot;
CONTAINS | &quot;contains&quot;
ELASTICSEARCH | &quot;elasticsearch&quot;



