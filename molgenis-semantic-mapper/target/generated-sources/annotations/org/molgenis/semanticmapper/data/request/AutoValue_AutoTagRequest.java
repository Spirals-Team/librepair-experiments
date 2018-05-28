
package org.molgenis.semanticmapper.data.request;

import java.util.List;
import javax.annotation.Generated;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_AutoTagRequest extends AutoTagRequest {

  private final String entityTypeId;
  private final List<String> ontologyIds;

  AutoValue_AutoTagRequest(
      String entityTypeId,
      List<String> ontologyIds) {
    if (entityTypeId == null) {
      throw new NullPointerException("Null entityTypeId");
    }
    this.entityTypeId = entityTypeId;
    if (ontologyIds == null) {
      throw new NullPointerException("Null ontologyIds");
    }
    this.ontologyIds = ontologyIds;
  }

  @NotBlank
  @Override
  public String getEntityTypeId() {
    return entityTypeId;
  }

  @NotEmpty
  @Override
  public List<String> getOntologyIds() {
    return ontologyIds;
  }

  @Override
  public String toString() {
    return "AutoTagRequest{"
        + "entityTypeId=" + entityTypeId + ", "
        + "ontologyIds=" + ontologyIds
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof AutoTagRequest) {
      AutoTagRequest that = (AutoTagRequest) o;
      return (this.entityTypeId.equals(that.getEntityTypeId()))
           && (this.ontologyIds.equals(that.getOntologyIds()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.entityTypeId.hashCode();
    h *= 1000003;
    h ^= this.ontologyIds.hashCode();
    return h;
  }

}
