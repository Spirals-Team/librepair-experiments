
package org.molgenis.searchall.model;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_PackageResult extends PackageResult {

  private final String label;
  private final String description;
  private final String id;

  AutoValue_PackageResult(
      String label,
      @Nullable String description,
      String id) {
    if (label == null) {
      throw new NullPointerException("Null label");
    }
    this.label = label;
    this.description = description;
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
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
  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return "PackageResult{"
        + "label=" + label + ", "
        + "description=" + description + ", "
        + "id=" + id
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof PackageResult) {
      PackageResult that = (PackageResult) o;
      return (this.label.equals(that.getLabel()))
           && ((this.description == null) ? (that.getDescription() == null) : this.description.equals(that.getDescription()))
           && (this.id.equals(that.getId()));
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
    h ^= this.id.hashCode();
    return h;
  }

}
