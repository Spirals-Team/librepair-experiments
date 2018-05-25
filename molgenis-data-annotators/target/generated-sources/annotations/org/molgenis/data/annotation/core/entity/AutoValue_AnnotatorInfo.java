
package org.molgenis.data.annotation.core.entity;

import java.util.List;
import javax.annotation.Generated;
import org.molgenis.data.meta.model.Attribute;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_AnnotatorInfo extends AnnotatorInfo {

  private final AnnotatorInfo.Status status;
  private final AnnotatorInfo.Type type;
  private final String code;
  private final String description;
  private final List<Attribute> outputAttributes;

  AutoValue_AnnotatorInfo(
      AnnotatorInfo.Status status,
      AnnotatorInfo.Type type,
      String code,
      String description,
      List<Attribute> outputAttributes) {
    if (status == null) {
      throw new NullPointerException("Null status");
    }
    this.status = status;
    if (type == null) {
      throw new NullPointerException("Null type");
    }
    this.type = type;
    if (code == null) {
      throw new NullPointerException("Null code");
    }
    this.code = code;
    if (description == null) {
      throw new NullPointerException("Null description");
    }
    this.description = description;
    if (outputAttributes == null) {
      throw new NullPointerException("Null outputAttributes");
    }
    this.outputAttributes = outputAttributes;
  }

  @Override
  public AnnotatorInfo.Status getStatus() {
    return status;
  }

  @Override
  public AnnotatorInfo.Type getType() {
    return type;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public List<Attribute> getOutputAttributes() {
    return outputAttributes;
  }

  @Override
  public String toString() {
    return "AnnotatorInfo{"
        + "status=" + status + ", "
        + "type=" + type + ", "
        + "code=" + code + ", "
        + "description=" + description + ", "
        + "outputAttributes=" + outputAttributes
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof AnnotatorInfo) {
      AnnotatorInfo that = (AnnotatorInfo) o;
      return (this.status.equals(that.getStatus()))
           && (this.type.equals(that.getType()))
           && (this.code.equals(that.getCode()))
           && (this.description.equals(that.getDescription()))
           && (this.outputAttributes.equals(that.getOutputAttributes()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.status.hashCode();
    h *= 1000003;
    h ^= this.type.hashCode();
    h *= 1000003;
    h ^= this.code.hashCode();
    h *= 1000003;
    h ^= this.description.hashCode();
    h *= 1000003;
    h ^= this.outputAttributes.hashCode();
    return h;
  }

}
