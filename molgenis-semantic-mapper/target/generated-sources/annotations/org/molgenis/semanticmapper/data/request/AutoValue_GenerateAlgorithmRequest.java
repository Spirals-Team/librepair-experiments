
package org.molgenis.semanticmapper.data.request;

import java.util.List;
import javax.annotation.Generated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_GenerateAlgorithmRequest extends GenerateAlgorithmRequest {

  private final String targetEntityTypeId;
  private final String targetAttributeName;
  private final String sourceEntityTypeId;
  private final List<String> sourceAttributes;

  AutoValue_GenerateAlgorithmRequest(
      String targetEntityTypeId,
      String targetAttributeName,
      String sourceEntityTypeId,
      List<String> sourceAttributes) {
    if (targetEntityTypeId == null) {
      throw new NullPointerException("Null targetEntityTypeId");
    }
    this.targetEntityTypeId = targetEntityTypeId;
    if (targetAttributeName == null) {
      throw new NullPointerException("Null targetAttributeName");
    }
    this.targetAttributeName = targetAttributeName;
    if (sourceEntityTypeId == null) {
      throw new NullPointerException("Null sourceEntityTypeId");
    }
    this.sourceEntityTypeId = sourceEntityTypeId;
    if (sourceAttributes == null) {
      throw new NullPointerException("Null sourceAttributes");
    }
    this.sourceAttributes = sourceAttributes;
  }

  @NotNull
  @NotNull
  @Override
  public String getTargetEntityTypeId() {
    return targetEntityTypeId;
  }

  @NotNull
  @NotNull
  @Override
  public String getTargetAttributeName() {
    return targetAttributeName;
  }

  @NotNull
  @NotNull
  @Override
  public String getSourceEntityTypeId() {
    return sourceEntityTypeId;
  }

  @NotEmpty
  @NotEmpty
  @Override
  public List<String> getSourceAttributes() {
    return sourceAttributes;
  }

  @Override
  public String toString() {
    return "GenerateAlgorithmRequest{"
        + "targetEntityTypeId=" + targetEntityTypeId + ", "
        + "targetAttributeName=" + targetAttributeName + ", "
        + "sourceEntityTypeId=" + sourceEntityTypeId + ", "
        + "sourceAttributes=" + sourceAttributes
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GenerateAlgorithmRequest) {
      GenerateAlgorithmRequest that = (GenerateAlgorithmRequest) o;
      return (this.targetEntityTypeId.equals(that.getTargetEntityTypeId()))
           && (this.targetAttributeName.equals(that.getTargetAttributeName()))
           && (this.sourceEntityTypeId.equals(that.getSourceEntityTypeId()))
           && (this.sourceAttributes.equals(that.getSourceAttributes()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.targetEntityTypeId.hashCode();
    h *= 1000003;
    h ^= this.targetAttributeName.hashCode();
    h *= 1000003;
    h ^= this.sourceEntityTypeId.hashCode();
    h *= 1000003;
    h ^= this.sourceAttributes.hashCode();
    return h;
  }

}
