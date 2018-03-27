package com.weaverplatform.protocol.model;

/**
 * @author Mohamad Alamili
 */
public abstract class CreatePropertyOperation extends WriteOperation {

  private String sourceId, key, replaceId, replacesId;
  private String sourceGraph, replaceGraph, replacesGraph;
  private boolean traverseReplaces;

  public CreatePropertyOperation(String user, String id, String sourceId, String key, String replaceId, String replacesId) {
    super(user, id);
    this.sourceId = sourceId;
    this.key = key;
    this.replaceId = replaceId;
    this.replacesId = replacesId;
  }

  public String getSourceId() {
    return sourceId;
  }

  public String getKey() {
    return key;
  }

  public boolean isReplacing() {
    return replaceId != null;
  }

  public String getReplaceId() {
    return replaceId;
  }

  public String getReplacesId() {
    return replacesId;
  }

  public void setTraverseReplaces(boolean traverseReplaces) {
    this.traverseReplaces = traverseReplaces;
  }

  public boolean getTraverseReplaces() {
    return traverseReplaces;
  }

  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setReplaceId(String replaceId) {
    this.replaceId = replaceId;
  }

  public void setReplacesId(String replacesId) {
    this.replacesId = replacesId;
  }

  public String getSourceGraph() {
    return sourceGraph;
  }

  public void setSourceGraph(String sourceGraph) {
    this.sourceGraph = sourceGraph;
  }

  public String getReplaceGraph() {
    return replaceGraph;
  }

  public void setReplaceGraph(String replaceGraph) {
    this.replaceGraph = replaceGraph;
  }

  public String getReplacesGraph() {
    return replacesGraph;
  }

  public void setReplacesGraph(String replacesGraph) {
    this.replacesGraph = replacesGraph;
  }

  public boolean isTraverseReplaces() {
    return traverseReplaces;
  }
}
