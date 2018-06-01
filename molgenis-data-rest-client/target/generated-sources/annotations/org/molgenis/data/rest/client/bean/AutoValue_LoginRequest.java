
package org.molgenis.data.rest.client.bean;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_LoginRequest extends LoginRequest {

  private final String username;
  private final String password;

  AutoValue_LoginRequest(
      String username,
      String password) {
    if (username == null) {
      throw new NullPointerException("Null username");
    }
    this.username = username;
    if (password == null) {
      throw new NullPointerException("Null password");
    }
    this.password = password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return "LoginRequest{"
        + "username=" + username + ", "
        + "password=" + password
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof LoginRequest) {
      LoginRequest that = (LoginRequest) o;
      return (this.username.equals(that.getUsername()))
           && (this.password.equals(that.getPassword()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.username.hashCode();
    h *= 1000003;
    h ^= this.password.hashCode();
    return h;
  }

}
