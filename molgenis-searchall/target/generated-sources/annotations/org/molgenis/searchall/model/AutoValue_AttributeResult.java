
package org.molgenis.searchall.model;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_AttributeResult extends AttributeResult {

  private final String label;
  private final String description;
  private final String dataType;

  AutoValue_AttributeResult(
      String label,
      @Nullable String description,
      String dataType) {
    if (label == null) {
      throw new NullPointerException("Null label");
    }
    this.label = label;
    this.description = description;
    if (dataType == null) {
      throw new NullPointerException("Null dataType");
    }
    this.dataType = dataType;
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Nullable
  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public String getDataType() {
    return dataType;
  }

  @Override
  public String toString() {
    return "AttributeResult{"
        + "label=" + label + ", "
        + "description=" + description + ", "
        + "dataType=" + dataType
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof AttributeResult) {
      AttributeResult that = (AttributeResult) o;
      return (this.label.equals(that.getLabel()))
           && ((this.description == null) ? (that.getDescription() == null) : this.description.equals(that.getDescription()))
           && (this.dataType.equals(that.getDataType()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.label.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    h *= 1000003;
    h ^= this.dataType.hashCode();
    return h;
  }

}
