package com.weaverplatform.protocol.model;

/**
 * @author Mohamad Alamili
 */
public class RemoveNodeOperation extends WriteOperation {

  private String removeId, removeGraph;
  private boolean cascade = true;

  public RemoveNodeOperation(String user, String id, String removeId) {
    super(user, id);
    this.removeId = removeId;
  }

  public String getRemoveId() {
    return removeId;
  }

  @Override
  public WriteOperationAction getAction() {
    return WriteOperationAction.REMOVE_NODE;
  }

  public boolean isCascade() {
    return cascade;
  }

  public void setRemoveId(String removeId) {
    this.removeId = removeId;
  }

  public String getRemoveGraph() {
    return removeGraph;
  }

  public void setRemoveGraph(String removeGraph) {
    this.removeGraph = removeGraph;
  }

  public void setCascade(boolean cascade) {
    this.cascade = cascade;
  }
}
