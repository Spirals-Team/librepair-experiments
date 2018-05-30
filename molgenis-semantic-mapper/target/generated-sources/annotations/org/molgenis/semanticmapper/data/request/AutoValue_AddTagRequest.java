
package org.molgenis.semanticmapper.data.request;

import java.util.List;
import javax.annotation.Generated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_AddTagRequest extends AddTagRequest {

  private final String entityTypeId;
  private final String attributeName;
  private final String relationIRI;
  private final List<String> ontologyTermIRIs;

  AutoValue_AddTagRequest(
      String entityTypeId,
      String attributeName,
      String relationIRI,
      List<String> ontologyTermIRIs) {
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
    if (ontologyTermIRIs == null) {
      throw new NullPointerException("Null ontologyTermIRIs");
    }
    this.ontologyTermIRIs = ontologyTermIRIs;
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
  public List<String> getOntologyTermIRIs() {
    return ontologyTermIRIs;
  }

  @Override
  public String toString() {
    return "AddTagRequest{"
        + "entityTypeId=" + entityTypeId + ", "
        + "attributeName=" + attributeName + ", "
        + "relationIRI=" + relationIRI + ", "
        + "ontologyTermIRIs=" + ontologyTermIRIs
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof AddTagRequest) {
      AddTagRequest that = (AddTagRequest) o;
      return (this.entityTypeId.equals(that.getEntityTypeId()))
           && (this.attributeName.equals(that.getAttributeName()))
           && (this.relationIRI.equals(that.getRelationIRI()))
           && (this.ontologyTermIRIs.equals(that.getOntologyTermIRIs()));
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
    h ^= this.ontologyTermIRIs.hashCode();
    return h;
  }

}
