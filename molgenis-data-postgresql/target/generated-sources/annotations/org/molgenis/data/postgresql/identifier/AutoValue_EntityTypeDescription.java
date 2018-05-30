
package org.molgenis.data.postgresql.identifier;

import com.google.common.collect.ImmutableMap;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EntityTypeDescription extends EntityTypeDescription {

  private final String id;
  private final ImmutableMap<String, AttributeDescription> attributeDescriptionMap;

  AutoValue_EntityTypeDescription(
      String id,
      ImmutableMap<String, AttributeDescription> attributeDescriptionMap) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    if (attributeDescriptionMap == null) {
      throw new NullPointerException("Null attributeDescriptionMap");
    }
    this.attributeDescriptionMap = attributeDescriptionMap;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public ImmutableMap<String, AttributeDescription> getAttributeDescriptionMap() {
    return attributeDescriptionMap;
  }

  @Override
  public String toString() {
    return "EntityTypeDescription{"
        + "id=" + id + ", "
        + "attributeDescriptionMap=" + attributeDescriptionMap
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EntityTypeDescription) {
      EntityTypeDescription that = (EntityTypeDescription) o;
      return (this.id.equals(that.getId()))
           && (this.attributeDescriptionMap.equals(that.getAttributeDescriptionMap()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= this.attributeDescriptionMap.hashCode();
    return h;
  }

}
