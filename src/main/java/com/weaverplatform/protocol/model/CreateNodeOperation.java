package com.weaverplatform.protocol.model;

/**
 * @author Mohamad Alamili
 */
public class CreateNodeOperation extends WriteOperation {

  private String createId;

  public CreateNodeOperation(String user, String id) {
    super(user, id);
  }

  public WriteOperationAction getAction(){
    return WriteOperationAction.CREATE_NODE;
  }
}
