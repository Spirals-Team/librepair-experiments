
package org.molgenis.data.rest.client.bean;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_LoginResponse extends LoginResponse {

  private final String token;
  private final String username;
  private final String firstname;
  private final String lastname;

  AutoValue_LoginResponse(
      String token,
      String username,
      @Nullable String firstname,
      @Nullable String lastname) {
    if (token == null) {
      throw new NullPointerException("Null token");
    }
    this.token = token;
    if (username == null) {
      throw new NullPointerException("Null username");
    }
    this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
  }

  @Override
  public String getToken() {
    return token;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Nullable
  @Override
  public String getFirstname() {
    return firstname;
  }

  @Nullable
  @Override
  public String getLastname() {
    return lastname;
  }

  @Override
  public String toString() {
    return "LoginResponse{"
        + "token=" + token + ", "
        + "username=" + username + ", "
        + "firstname=" + firstname + ", "
        + "lastname=" + lastname
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof LoginResponse) {
      LoginResponse that = (LoginResponse) o;
      return (this.token.equals(that.getToken()))
           && (this.username.equals(that.getUsername()))
           && ((this.firstname == null) ? (that.getFirstname() == null) : this.firstname.equals(that.getFirstname()))
           && ((this.lastname == null) ? (that.getLastname() == null) : this.lastname.equals(that.getLastname()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.token.hashCode();
    h *= 1000003;
    h ^= this.username.hashCode();
    h *= 1000003;
    h ^= (firstname == null) ? 0 : this.firstname.hashCode();
    h *= 1000003;
    h ^= (lastname == null) ? 0 : this.lastname.hashCode();
    return h;
  }

}
