
package org.molgenis.data.rest.v2;

import java.util.List;
import javax.annotation.Generated;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EntityCollectionDeleteRequestV2 extends EntityCollectionDeleteRequestV2 {

  private final List<String> entityIds;

  AutoValue_EntityCollectionDeleteRequestV2(
      List<String> entityIds) {
    if (entityIds == null) {
      throw new NullPointerException("Null entityIds");
    }
    this.entityIds = entityIds;
  }

  @NotEmpty(message = "Please provide at least one entity in the entityIds property.")
  @Size(max = 1000, message = "Number of entity identifiers cannot be more than {max}.")
  @Override
  public List<String> getEntityIds() {
    return entityIds;
  }

  @Override
  public String toString() {
    return "EntityCollectionDeleteRequestV2{"
        + "entityIds=" + entityIds
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EntityCollectionDeleteRequestV2) {
      EntityCollectionDeleteRequestV2 that = (EntityCollectionDeleteRequestV2) o;
      return (this.entityIds.equals(that.getEntityIds()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.entityIds.hashCode();
    return h;
  }

}
