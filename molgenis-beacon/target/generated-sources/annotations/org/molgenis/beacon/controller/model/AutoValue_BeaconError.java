
package org.molgenis.beacon.controller.model;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_BeaconError extends BeaconError {

  private final Integer errorCode;
  private final String message;

  AutoValue_BeaconError(
      Integer errorCode,
      String message) {
    if (errorCode == null) {
      throw new NullPointerException("Null errorCode");
    }
    this.errorCode = errorCode;
    if (message == null) {
      throw new NullPointerException("Null message");
    }
    this.message = message;
  }

  @Override
  public Integer getErrorCode() {
    return errorCode;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "BeaconError{"
        + "errorCode=" + errorCode + ", "
        + "message=" + message
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof BeaconError) {
      BeaconError that = (BeaconError) o;
      return (this.errorCode.equals(that.getErrorCode()))
           && (this.message.equals(that.getMessage()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.errorCode.hashCode();
    h *= 1000003;
    h ^= this.message.hashCode();
    return h;
  }

}
