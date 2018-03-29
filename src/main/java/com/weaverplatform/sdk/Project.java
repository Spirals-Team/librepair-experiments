package com.weaverplatform.sdk;

/**
 * Created by gijs on 08/06/2017.
 */
public class Project {
  private String id;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  private String name;

  public Project(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
