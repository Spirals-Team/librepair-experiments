
package org.molgenis.dataexplorer.negotiator;

import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_ExportValidationResponse extends ExportValidationResponse {

  private final boolean isValid;
  private final String message;
  private final List<String> enabledCollections;
  private final List<String> disabledCollections;

  AutoValue_ExportValidationResponse(
      boolean isValid,
      String message,
      List<String> enabledCollections,
      List<String> disabledCollections) {
    this.isValid = isValid;
    if (message == null) {
      throw new NullPointerException("Null message");
    }
    this.message = message;
    if (enabledCollections == null) {
      throw new NullPointerException("Null enabledCollections");
    }
    this.enabledCollections = enabledCollections;
    if (disabledCollections == null) {
      throw new NullPointerException("Null disabledCollections");
    }
    this.disabledCollections = disabledCollections;
  }

  @Override
  public boolean isValid() {
    return isValid;
  }

  @Override
  public String message() {
    return message;
  }

  @Override
  public List<String> enabledCollections() {
    return enabledCollections;
  }

  @Override
  public List<String> disabledCollections() {
    return disabledCollections;
  }

  @Override
  public String toString() {
    return "ExportValidationResponse{"
        + "isValid=" + isValid + ", "
        + "message=" + message + ", "
        + "enabledCollections=" + enabledCollections + ", "
        + "disabledCollections=" + disabledCollections
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof ExportValidationResponse) {
      ExportValidationResponse that = (ExportValidationResponse) o;
      return (this.isValid == that.isValid())
           && (this.message.equals(that.message()))
           && (this.enabledCollections.equals(that.enabledCollections()))
           && (this.disabledCollections.equals(that.disabledCollections()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.isValid ? 1231 : 1237;
    h *= 1000003;
    h ^= this.message.hashCode();
    h *= 1000003;
    h ^= this.enabledCollections.hashCode();
    h *= 1000003;
    h ^= this.disabledCollections.hashCode();
    return h;
  }

}
