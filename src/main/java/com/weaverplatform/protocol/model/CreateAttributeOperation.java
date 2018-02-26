package com.weaverplatform.protocol.model;

/**
 * @author Mohamad Alamili
 */
public class CreateAttributeOperation extends CreatePropertyOperation {

  private Object value;
  private AttributeDataType datatype;

  public CreateAttributeOperation(String user, String id, String sourceId, String key,  Object value, AttributeDataType dataType) {
    this(user, id, sourceId, key, value, dataType, null, null);
  }

  public CreateAttributeOperation(String user, String id, String sourceId, String key, Object value, AttributeDataType dataType, String replaceId, String replacesId) {
    super(user, id, sourceId, key, replaceId, replacesId);
    this.value = value;
    this.datatype = dataType;
  }

  public WriteOperationAction getAction(){
    return WriteOperationAction.CREATE_ATTRIBUTE;
  }

  public Object getValue() {
    return value;
  }

  public AttributeDataType getDatatype() {
    return datatype;
  }

}
