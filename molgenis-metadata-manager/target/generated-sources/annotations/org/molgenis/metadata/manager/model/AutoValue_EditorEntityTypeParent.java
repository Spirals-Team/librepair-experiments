
package org.molgenis.metadata.manager.model;

import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EditorEntityTypeParent extends EditorEntityTypeParent {

  private final String id;
  private final String label;
  private final List<EditorAttributeIdentifier> attributes;
  private final EditorEntityTypeParent parent;

  AutoValue_EditorEntityTypeParent(
      String id,
      @Nullable String label,
      List<EditorAttributeIdentifier> attributes,
      @Nullable EditorEntityTypeParent parent) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    this.label = label;
    if (attributes == null) {
      throw new NullPointerException("Null attributes");
    }
    this.attributes = attributes;
    this.parent = parent;
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
  public List<EditorAttributeIdentifier> getAttributes() {
    return attributes;
  }

  @Nullable
  @Override
  public EditorEntityTypeParent getParent() {
    return parent;
  }

  @Override
  public String toString() {
    return "EditorEntityTypeParent{"
        + "id=" + id + ", "
        + "label=" + label + ", "
        + "attributes=" + attributes + ", "
        + "parent=" + parent
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EditorEntityTypeParent) {
      EditorEntityTypeParent that = (EditorEntityTypeParent) o;
      return (this.id.equals(that.getId()))
           && ((this.label == null) ? (that.getLabel() == null) : this.label.equals(that.getLabel()))
           && (this.attributes.equals(that.getAttributes()))
           && ((this.parent == null) ? (that.getParent() == null) : this.parent.equals(that.getParent()));
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
    h ^= this.attributes.hashCode();
    h *= 1000003;
    h ^= (parent == null) ? 0 : this.parent.hashCode();
    return h;
  }

}
