
package org.molgenis.semanticsearch.semantic;

import javax.annotation.Generated;
import org.molgenis.ontology.core.model.OntologyTerm;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_OntologyTag extends OntologyTag {

  private final OntologyTerm ontologyTerm;
  private final String relationIRI;

  AutoValue_OntologyTag(
      OntologyTerm ontologyTerm,
      String relationIRI) {
    if (ontologyTerm == null) {
      throw new NullPointerException("Null ontologyTerm");
    }
    this.ontologyTerm = ontologyTerm;
    if (relationIRI == null) {
      throw new NullPointerException("Null relationIRI");
    }
    this.relationIRI = relationIRI;
  }

  @Override
  public OntologyTerm getOntologyTerm() {
    return ontologyTerm;
  }

  @Override
  public String getRelationIRI() {
    return relationIRI;
  }

  @Override
  public String toString() {
    return "OntologyTag{"
        + "ontologyTerm=" + ontologyTerm + ", "
        + "relationIRI=" + relationIRI
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof OntologyTag) {
      OntologyTag that = (OntologyTag) o;
      return (this.ontologyTerm.equals(that.getOntologyTerm()))
           && (this.relationIRI.equals(that.getRelationIRI()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.ontologyTerm.hashCode();
    h *= 1000003;
    h ^= this.relationIRI.hashCode();
    return h;
  }

}
