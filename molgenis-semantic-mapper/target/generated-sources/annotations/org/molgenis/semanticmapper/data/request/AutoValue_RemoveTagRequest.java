
package org.molgenis.semanticmapper.data.request;

import javax.annotation.Generated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_RemoveTagRequest extends RemoveTagRequest {

  private final String entityTypeId;
  private final String attributeName;
  private final String relationIRI;
  private final String ontologyTermIRI;

  AutoValue_RemoveTagRequest(
      String entityTypeId,
      String attributeName,
      String relationIRI,
      String ontologyTermIRI) {
    if (entityTypeId == null) {
      throw new NullPointerException("Null entityTypeId");
    }
    this.entityTypeId = entityTypeId;
    if (attributeName == null) {
      throw new NullPointerException("Null attributeName");
    }
    this.attributeName = attributeName;
    if (relationIRI == null) {
      throw new NullPointerException("Null relationIRI");
    }
    this.relationIRI = relationIRI;
    if (ontologyTermIRI == null) {
      throw new NullPointerException("Null ontologyTermIRI");
    }
    this.ontologyTermIRI = ontologyTermIRI;
  }

  @NotNull
  @NotNull
  @Override
  public String getEntityTypeId() {
    return entityTypeId;
  }

  @NotNull
  @NotNull
  @Override
  public String getAttributeName() {
    return attributeName;
  }

  @NotNull
  @NotNull
  @Override
  public String getRelationIRI() {
    return relationIRI;
  }

  @NotEmpty
  @NotEmpty
  @Override
  public String getOntologyTermIRI() {
    return ontologyTermIRI;
  }

  @Override
  public String toString() {
    return "RemoveTagRequest{"
        + "entityTypeId=" + entityTypeId + ", "
        + "attributeName=" + attributeName + ", "
        + "relationIRI=" + relationIRI + ", "
        + "ontologyTermIRI=" + ontologyTermIRI
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof RemoveTagRequest) {
      RemoveTagRequest that = (RemoveTagRequest) o;
      return (this.entityTypeId.equals(that.getEntityTypeId()))
           && (this.attributeName.equals(that.getAttributeName()))
           && (this.relationIRI.equals(that.getRelationIRI()))
           && (this.ontologyTermIRI.equals(that.getOntologyTermIRI()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.entityTypeId.hashCode();
    h *= 1000003;
    h ^= this.attributeName.hashCode();
    h *= 1000003;
    h ^= this.relationIRI.hashCode();
    h *= 1000003;
    h ^= this.ontologyTermIRI.hashCode();
    return h;
  }

}
