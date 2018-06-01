
package org.molgenis.dataexplorer.negotiator;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Collection extends Collection {

  private final String collectionId;
  private final String biobankId;

  AutoValue_Collection(
      String collectionId,
      @Nullable String biobankId) {
    if (collectionId == null) {
      throw new NullPointerException("Null collectionId");
    }
    this.collectionId = collectionId;
    this.biobankId = biobankId;
  }

  @Override
  public String getCollectionId() {
    return collectionId;
  }

  @Nullable
  @Override
  public String getBiobankId() {
    return biobankId;
  }

  @Override
  public String toString() {
    return "Collection{"
        + "collectionId=" + collectionId + ", "
        + "biobankId=" + biobankId
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Collection) {
      Collection that = (Collection) o;
      return (this.collectionId.equals(that.getCollectionId()))
           && ((this.biobankId == null) ? (that.getBiobankId() == null) : this.biobankId.equals(that.getBiobankId()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.collectionId.hashCode();
    h *= 1000003;
    h ^= (biobankId == null) ? 0 : this.biobankId.hashCode();
    return h;
  }

}
