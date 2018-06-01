
package org.molgenis.data.index;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Impact extends Impact {

  private final String entityTypeId;
  private final Object id;

  AutoValue_Impact(
      String entityTypeId,
      @Nullable Object id) {
    if (entityTypeId == null) {
      throw new NullPointerException("Null entityTypeId");
    }
    this.entityTypeId = entityTypeId;
    this.id = id;
  }

  @Override
  public String getEntityTypeId() {
    return entityTypeId;
  }

  @Nullable
  @Override
  public Object getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Impact{"
        + "entityTypeId=" + entityTypeId + ", "
        + "id=" + id
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Impact) {
      Impact that = (Impact) o;
      return (this.entityTypeId.equals(that.getEntityTypeId()))
           && ((this.id == null) ? (that.getId() == null) : this.id.equals(that.getId()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.entityTypeId.hashCode();
    h *= 1000003;
    h ^= (id == null) ? 0 : this.id.hashCode();
    return h;
  }

}
