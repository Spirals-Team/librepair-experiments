
package org.molgenis.data;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EntityKey extends EntityKey {

  private final String entityTypeId;
  private final Object id;

  AutoValue_EntityKey(
      String entityTypeId,
      Object id) {
    if (entityTypeId == null) {
      throw new NullPointerException("Null entityTypeId");
    }
    this.entityTypeId = entityTypeId;
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
  }

  @Override
  public String getEntityTypeId() {
    return entityTypeId;
  }

  @Override
  public Object getId() {
    return id;
  }

  @Override
  public String toString() {
    return "EntityKey{"
        + "entityTypeId=" + entityTypeId + ", "
        + "id=" + id
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EntityKey) {
      EntityKey that = (EntityKey) o;
      return (this.entityTypeId.equals(that.getEntityTypeId()))
           && (this.id.equals(that.getId()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.entityTypeId.hashCode();
    h *= 1000003;
    h ^= this.id.hashCode();
    return h;
  }

}
