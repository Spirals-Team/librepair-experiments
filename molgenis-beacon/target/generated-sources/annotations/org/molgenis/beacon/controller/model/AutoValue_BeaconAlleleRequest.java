
package org.molgenis.beacon.controller.model;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_BeaconAlleleRequest extends BeaconAlleleRequest {

  private final String referenceName;
  private final Long start;
  private final String referenceBases;
  private final String alternateBases;

  AutoValue_BeaconAlleleRequest(
      String referenceName,
      Long start,
      String referenceBases,
      String alternateBases) {
    if (referenceName == null) {
      throw new NullPointerException("Null referenceName");
    }
    this.referenceName = referenceName;
    if (start == null) {
      throw new NullPointerException("Null start");
    }
    this.start = start;
    if (referenceBases == null) {
      throw new NullPointerException("Null referenceBases");
    }
    this.referenceBases = referenceBases;
    if (alternateBases == null) {
      throw new NullPointerException("Null alternateBases");
    }
    this.alternateBases = alternateBases;
  }

  @Override
  public String getReferenceName() {
    return referenceName;
  }

  @Override
  public Long getStart() {
    return start;
  }

  @Override
  public String getReferenceBases() {
    return referenceBases;
  }

  @Override
  public String getAlternateBases() {
    return alternateBases;
  }

  @Override
  public String toString() {
    return "BeaconAlleleRequest{"
        + "referenceName=" + referenceName + ", "
        + "start=" + start + ", "
        + "referenceBases=" + referenceBases + ", "
        + "alternateBases=" + alternateBases
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof BeaconAlleleRequest) {
      BeaconAlleleRequest that = (BeaconAlleleRequest) o;
      return (this.referenceName.equals(that.getReferenceName()))
           && (this.start.equals(that.getStart()))
           && (this.referenceBases.equals(that.getReferenceBases()))
           && (this.alternateBases.equals(that.getAlternateBases()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.referenceName.hashCode();
    h *= 1000003;
    h ^= this.start.hashCode();
    h *= 1000003;
    h ^= this.referenceBases.hashCode();
    h *= 1000003;
    h ^= this.alternateBases.hashCode();
    return h;
  }

}
