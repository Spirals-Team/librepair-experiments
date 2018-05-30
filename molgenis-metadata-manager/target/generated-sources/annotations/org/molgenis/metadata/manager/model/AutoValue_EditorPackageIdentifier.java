
package org.molgenis.metadata.manager.model;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EditorPackageIdentifier extends EditorPackageIdentifier {

  private final String id;
  private final String label;

  AutoValue_EditorPackageIdentifier(
      String id,
      @Nullable String label) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    this.label = label;
  }

  @Override
  public String getId() {
    return id;
  }

  @Nullable
  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public String toString() {
    return "EditorPackageIdentifier{"
        + "id=" + id + ", "
        + "label=" + label
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EditorPackageIdentifier) {
      EditorPackageIdentifier that = (EditorPackageIdentifier) o;
      return (this.id.equals(that.getId()))
           && ((this.label == null) ? (that.getLabel() == null) : this.label.equals(that.getLabel()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= (label == null) ? 0 : this.label.hashCode();
    return h;
  }

}
