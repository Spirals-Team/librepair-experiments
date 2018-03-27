package com.weaverplatform.protocol.model;

/**
 * @author Mohamad Alamili
 */
public class RemoveAttributeOperation extends RemoveNodeOperation {

  public RemoveAttributeOperation(String user, String id, String removeId) {
    super(user, id, removeId);
  }

  public WriteOperationAction getAction(){
    return WriteOperationAction.REMOVE_ATTRIBUTE;
  }
}
