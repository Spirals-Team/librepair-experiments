
package org.molgenis.beacon.controller.model;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_BeaconDatasetResponse extends BeaconDatasetResponse {

  private final String id;
  private final String name;
  private final String description;

  AutoValue_BeaconDatasetResponse(
      String id,
      String name,
      @Nullable String description) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
    this.description = description;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Nullable
  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "BeaconDatasetResponse{"
        + "id=" + id + ", "
        + "name=" + name + ", "
        + "description=" + description
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof BeaconDatasetResponse) {
      BeaconDatasetResponse that = (BeaconDatasetResponse) o;
      return (this.id.equals(that.getId()))
           && (this.name.equals(that.getName()))
           && ((this.description == null) ? (that.getDescription() == null) : this.description.equals(that.getDescription()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= this.name.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    return h;
  }

}
