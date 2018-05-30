
package org.molgenis.data.importer;

import com.google.common.collect.ImmutableMap;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_PersistResult extends PersistResult {

  private final ImmutableMap<String, Long> nrPersistedEntitiesMap;

  AutoValue_PersistResult(
      ImmutableMap<String, Long> nrPersistedEntitiesMap) {
    if (nrPersistedEntitiesMap == null) {
      throw new NullPointerException("Null nrPersistedEntitiesMap");
    }
    this.nrPersistedEntitiesMap = nrPersistedEntitiesMap;
  }

  @Override
  public ImmutableMap<String, Long> getNrPersistedEntitiesMap() {
    return nrPersistedEntitiesMap;
  }

  @Override
  public String toString() {
    return "PersistResult{"
        + "nrPersistedEntitiesMap=" + nrPersistedEntitiesMap
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof PersistResult) {
      PersistResult that = (PersistResult) o;
      return (this.nrPersistedEntitiesMap.equals(that.getNrPersistedEntitiesMap()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.nrPersistedEntitiesMap.hashCode();
    return h;
  }

}
