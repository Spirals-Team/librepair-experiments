
package org.molgenis.security.core.model;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_UserValue extends UserValue {

  private final String username;

  AutoValue_UserValue(
      String username) {
    if (username == null) {
      throw new NullPointerException("Null username");
    }
    this.username = username;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String toString() {
    return "UserValue{"
        + "username=" + username
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof UserValue) {
      UserValue that = (UserValue) o;
      return (this.username.equals(that.getUsername()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.username.hashCode();
    return h;
  }

}
