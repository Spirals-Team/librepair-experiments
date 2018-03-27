package com.weaverplatform.protocol.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * @author Mohamad Alamili
 */
public abstract class WriteOperation {
  private WriteOperationAction action;
  private String user, id, graph;
  private long timestamp;

  public WriteOperation(String user, String id) {
    this.user = user;
    this.id = id;
    this.timestamp = System.currentTimeMillis();
    this.action = this.getAction();
  }

  public abstract WriteOperationAction getAction();

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

  public long getTimestamp() {
    return timestamp;
  }

  public enum WriteOperationAction {
    @SerializedName("create-node")                CREATE_NODE("create-node"),
    @SerializedName("create-relation")            CREATE_RELATION("create-relation"),
    @SerializedName("create-attribute")           CREATE_ATTRIBUTE("create-attribute"),
    @SerializedName("remove-node")                REMOVE_NODE("remove-node"),
    @SerializedName("remove-node-unrecoverable")  REMOVE_NODE_UNRECOVERABLE("remove-node-unrecoverable"),
    @SerializedName("remove-relation")            REMOVE_RELATION("remove-relation"),
    @SerializedName("remove-attribute")           REMOVE_ATTRIBUTE("remove-attribute");

    private String value;

    WriteOperationAction(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }
}
