
package org.molgenis.semanticmapper.data.request;

import java.util.List;
import javax.annotation.Generated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_GetOntologyTermRequest extends GetOntologyTermRequest {

  private final String searchTerm;
  private final List<String> ontologyIds;

  AutoValue_GetOntologyTermRequest(
      String searchTerm,
      List<String> ontologyIds) {
    if (searchTerm == null) {
      throw new NullPointerException("Null searchTerm");
    }
    this.searchTerm = searchTerm;
    if (ontologyIds == null) {
      throw new NullPointerException("Null ontologyIds");
    }
    this.ontologyIds = ontologyIds;
  }

  @NotBlank
  @NotBlank
  @Override
  public String getSearchTerm() {
    return searchTerm;
  }

  @NotEmpty
  @NotEmpty
  @Override
  public List<String> getOntologyIds() {
    return ontologyIds;
  }

  @Override
  public String toString() {
    return "GetOntologyTermRequest{"
        + "searchTerm=" + searchTerm + ", "
        + "ontologyIds=" + ontologyIds
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GetOntologyTermRequest) {
      GetOntologyTermRequest that = (GetOntologyTermRequest) o;
      return (this.searchTerm.equals(that.getSearchTerm()))
           && (this.ontologyIds.equals(that.getOntologyIds()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.searchTerm.hashCode();
    h *= 1000003;
    h ^= this.ontologyIds.hashCode();
    return h;
  }

}
