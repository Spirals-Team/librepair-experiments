package com.weaverplatform.protocol.model;

/**
 * @author Mohamad Alamili
 */
public class RemoveRelationOperation extends RemoveNodeOperation {

  public RemoveRelationOperation(String user, String id, String removeId) {
    super(user, id, removeId);
  }

  public WriteOperationAction getAction(){
    return WriteOperationAction.REMOVE_RELATION;
  }
}
