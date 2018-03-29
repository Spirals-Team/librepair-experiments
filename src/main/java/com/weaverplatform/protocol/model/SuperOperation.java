package com.weaverplatform.protocol.model;

import com.google.gson.Gson;

/**
 * @author Mohamad Alamili
 */
public class SuperOperation {

  private String action;

  private String user, id, graph;
  private long timestamp;

  private String sourceId, key, replaceId, replacesId;
  private String sourceGraph, replaceGraph, replacesGraph;
  private boolean traverseReplaces;

  private Object value;
  private AttributeDataType datatype;

  private String targetId, targetGraph;


  private String removeId, removeGraph;
  private boolean cascade = true;

  public String getAction() {
    return action;
  }
  public void setAction(String action) {
    this.action = action;
  }

  public String toJson() {
    return new Gson().toJson(this);
  }

  public String getUser() {
    return user;
  }

  public String getGraph() {
    return graph;
  }

  public void setGraph(String graph) {
    this.graph = graph;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public long getTimestamp() {
    return timestamp;
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

  public void setValue(Object value) {
    this.value = value;
  }

  public void setDatatype(AttributeDataType dataType) {
    this.datatype = dataType;
  }


  public Object getValue() {
    return value;
  }

  public AttributeDataType getDatatype() {
    return datatype;
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
  public String getRemoveId() {
    return removeId;
  }
}
