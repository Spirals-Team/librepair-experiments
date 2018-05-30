
package org.molgenis.ontology.core.model;

import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_OntologyTerm extends OntologyTerm {

  private final String IRI;
  private final String label;
  private final String description;
  private final List<String> synonyms;

  AutoValue_OntologyTerm(
      String IRI,
      String label,
      @Nullable String description,
      List<String> synonyms) {
    if (IRI == null) {
      throw new NullPointerException("Null IRI");
    }
    this.IRI = IRI;
    if (label == null) {
      throw new NullPointerException("Null label");
    }
    this.label = label;
    this.description = description;
    if (synonyms == null) {
      throw new NullPointerException("Null synonyms");
    }
    this.synonyms = synonyms;
  }

  @Override
  public String getIRI() {
    return IRI;
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Nullable
  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public List<String> getSynonyms() {
    return synonyms;
  }

  @Override
  public String toString() {
    return "OntologyTerm{"
        + "IRI=" + IRI + ", "
        + "label=" + label + ", "
        + "description=" + description + ", "
        + "synonyms=" + synonyms
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof OntologyTerm) {
      OntologyTerm that = (OntologyTerm) o;
      return (this.IRI.equals(that.getIRI()))
           && (this.label.equals(that.getLabel()))
           && ((this.description == null) ? (that.getDescription() == null) : this.description.equals(that.getDescription()))
           && (this.synonyms.equals(that.getSynonyms()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.IRI.hashCode();
    h *= 1000003;
    h ^= this.label.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    h *= 1000003;
    h ^= this.synonyms.hashCode();
    return h;
  }

}
