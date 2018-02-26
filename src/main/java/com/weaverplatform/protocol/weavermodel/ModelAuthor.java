package com.weaverplatform.protocol.weavermodel;

/**
 * @author bastbijl, Sysunite 2017
 */
public class ModelAuthor {

  private String name;
  private String email;

  public ModelAuthor() {
  }

  public ModelAuthor(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
