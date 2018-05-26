
package com.arcao.geocaching.api;

import javax.annotation.Generated;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Status extends Status {

  private final int code;
  private final String message;
  private final String exceptionDetails;

  private AutoValue_Status(
      int code,
      String message,
      @Nullable String exceptionDetails) {
    this.code = code;
    this.message = message;
    this.exceptionDetails = exceptionDetails;
  }

  @Override
  public int code() {
    return code;
  }

  @Override
  public String message() {
    return message;
  }

  @Nullable
  @Override
  public String exceptionDetails() {
    return exceptionDetails;
  }

  @Override
  public String toString() {
    return "Status{"
        + "code=" + code + ", "
        + "message=" + message + ", "
        + "exceptionDetails=" + exceptionDetails
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Status) {
      Status that = (Status) o;
      return (this.code == that.code())
           && (this.message.equals(that.message()))
           && ((this.exceptionDetails == null) ? (that.exceptionDetails() == null) : this.exceptionDetails.equals(that.exceptionDetails()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.code;
    h *= 1000003;
    h ^= this.message.hashCode();
    h *= 1000003;
    h ^= (exceptionDetails == null) ? 0 : this.exceptionDetails.hashCode();
    return h;
  }

  private static final long serialVersionUID = -6603595166996374235L;

  static final class Builder extends Status.Builder {
    private Integer code;
    private String message;
    private String exceptionDetails;
    Builder() {
    }
    @Override
    public Status.Builder code(int code) {
      this.code = code;
      return this;
    }
    @Override
    public Status.Builder message(String message) {
      if (message == null) {
        throw new NullPointerException("Null message");
      }
      this.message = message;
      return this;
    }
    @Override
    public Status.Builder exceptionDetails(@Nullable String exceptionDetails) {
      this.exceptionDetails = exceptionDetails;
      return this;
    }
    @Override
    public Status build() {
      String missing = "";
      if (this.code == null) {
        missing += " code";
      }
      if (this.message == null) {
        missing += " message";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_Status(
          this.code,
          this.message,
          this.exceptionDetails);
    }
  }

}
