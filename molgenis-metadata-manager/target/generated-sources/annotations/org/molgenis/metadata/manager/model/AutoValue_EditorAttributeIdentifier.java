
package org.molgenis.metadata.manager.model;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EditorAttributeIdentifier extends EditorAttributeIdentifier {

  private final String id;
  private final String label;
  private final EditorEntityTypeIdentifier entity;

  AutoValue_EditorAttributeIdentifier(
      String id,
      @Nullable String label,
      @Nullable EditorEntityTypeIdentifier entity) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    this.label = label;
    this.entity = entity;
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

  @Nullable
  @Override
  public EditorEntityTypeIdentifier getEntity() {
    return entity;
  }

  @Override
  public String toString() {
    return "EditorAttributeIdentifier{"
        + "id=" + id + ", "
        + "label=" + label + ", "
        + "entity=" + entity
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EditorAttributeIdentifier) {
      EditorAttributeIdentifier that = (EditorAttributeIdentifier) o;
      return (this.id.equals(that.getId()))
           && ((this.label == null) ? (that.getLabel() == null) : this.label.equals(that.getLabel()))
           && ((this.entity == null) ? (that.getEntity() == null) : this.entity.equals(that.getEntity()));
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
    h *= 1000003;
    h ^= (entity == null) ? 0 : this.entity.hashCode();
    return h;
  }

}
