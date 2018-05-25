
package org.molgenis.metadata.manager.model;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EditorTagIdentifier extends EditorTagIdentifier {

  private final String id;
  private final String label;

  AutoValue_EditorTagIdentifier(
      String id,
      String label) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    if (label == null) {
      throw new NullPointerException("Null label");
    }
    this.label = label;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public String toString() {
    return "EditorTagIdentifier{"
        + "id=" + id + ", "
        + "label=" + label
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EditorTagIdentifier) {
      EditorTagIdentifier that = (EditorTagIdentifier) o;
      return (this.id.equals(that.getId()))
           && (this.label.equals(that.getLabel()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= this.label.hashCode();
    return h;
  }

}
