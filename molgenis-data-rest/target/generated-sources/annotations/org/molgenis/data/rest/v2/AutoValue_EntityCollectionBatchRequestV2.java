
package org.molgenis.data.rest.v2;

import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EntityCollectionBatchRequestV2 extends EntityCollectionBatchRequestV2 {

  private final List<Map<String, Object>> entities;

  AutoValue_EntityCollectionBatchRequestV2(
      @Nullable List<Map<String, Object>> entities) {
    this.entities = entities;
  }

  @Nullable
  @NotEmpty(message = "Please provide at least one entity in the entities property.")
  @Size(max = 1000, message = "Number of entities cannot be more than {max}.")
  @Override
  public List<Map<String, Object>> getEntities() {
    return entities;
  }

  @Override
  public String toString() {
    return "EntityCollectionBatchRequestV2{"
        + "entities=" + entities
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EntityCollectionBatchRequestV2) {
      EntityCollectionBatchRequestV2 that = (EntityCollectionBatchRequestV2) o;
      return ((this.entities == null) ? (that.getEntities() == null) : this.entities.equals(that.getEntities()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (entities == null) ? 0 : this.entities.hashCode();
    return h;
  }

}
