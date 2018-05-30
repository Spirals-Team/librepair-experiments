
package org.molgenis.metadata.manager.model;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EditorOrder extends EditorOrder {

  private final String attributeName;
  private final String direction;

  AutoValue_EditorOrder(
      String attributeName,
      @Nullable String direction) {
    if (attributeName == null) {
      throw new NullPointerException("Null attributeName");
    }
    this.attributeName = attributeName;
    this.direction = direction;
  }

  @Override
  public String getAttributeName() {
    return attributeName;
  }

  @Nullable
  @Override
  public String getDirection() {
    return direction;
  }

  @Override
  public String toString() {
    return "EditorOrder{"
        + "attributeName=" + attributeName + ", "
        + "direction=" + direction
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EditorOrder) {
      EditorOrder that = (EditorOrder) o;
      return (this.attributeName.equals(that.getAttributeName()))
           && ((this.direction == null) ? (that.getDirection() == null) : this.direction.equals(that.getDirection()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.attributeName.hashCode();
    h *= 1000003;
    h ^= (direction == null) ? 0 : this.direction.hashCode();
    return h;
  }

}
