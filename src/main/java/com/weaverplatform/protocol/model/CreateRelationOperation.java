package com.weaverplatform.protocol.model;

/**
 * @author Mohamad Alamili
 */
public class CreateRelationOperation extends CreatePropertyOperation {

  private String targetId, targetGraph;

  public CreateRelationOperation(String user, String id, String sourceId, String key, String targetId) {
    this(user, id, sourceId, key, targetId, null, null);
  }

  public CreateRelationOperation(String user, String id, String sourceId, String key, String targetId, String replaceId, String replacesId) {
    super(user, id, sourceId, key, replaceId, replacesId);
    this.targetId = targetId;
  }

  public WriteOperationAction getAction(){
    return WriteOperationAction.CREATE_RELATION;
  }

  public void setTargetId(String targetId) {
    this.targetId = targetId;
  }

  public String getTargetGraph() {
    return targetGraph;
  }

  public void setTargetGraph(String targetGraph) {
    this.targetGraph = targetGraph;
  }

  public String getTargetId() {
    return targetId;
  }
}
