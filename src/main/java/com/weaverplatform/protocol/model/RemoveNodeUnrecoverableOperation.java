package com.weaverplatform.protocol.model;

/**
 * @author Mathieu Brouwers
 */
public class RemoveNodeUnrecoverableOperation extends WriteOperation {

  private boolean cascade = true;

  public RemoveNodeUnrecoverableOperation(String user, String id) {
    super(user, id);
  }

  @Override
  public WriteOperationAction getAction() {
    return WriteOperationAction.REMOVE_NODE_UNRECOVERABLE;
  }

  public boolean isCascade() {
    return cascade;
  }

  public void setCascade(boolean cascade) {
    this.cascade = cascade;
  }
}
