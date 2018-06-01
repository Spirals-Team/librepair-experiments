
package org.molgenis.ontology.core.model;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Ontology extends Ontology {

  private final String id;
  private final String IRI;
  private final String name;

  AutoValue_Ontology(
      String id,
      String IRI,
      String name) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    if (IRI == null) {
      throw new NullPointerException("Null IRI");
    }
    this.IRI = IRI;
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getIRI() {
    return IRI;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Ontology{"
        + "id=" + id + ", "
        + "IRI=" + IRI + ", "
        + "name=" + name
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Ontology) {
      Ontology that = (Ontology) o;
      return (this.id.equals(that.getId()))
           && (this.IRI.equals(that.getIRI()))
           && (this.name.equals(that.getName()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= this.IRI.hashCode();
    h *= 1000003;
    h ^= this.name.hashCode();
    return h;
  }

}
