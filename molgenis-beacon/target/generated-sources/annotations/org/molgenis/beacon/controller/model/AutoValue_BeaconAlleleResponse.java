
package org.molgenis.beacon.controller.model;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_BeaconAlleleResponse extends BeaconAlleleResponse {

  private final String beaconId;
  private final Boolean exists;
  private final BeaconError error;
  private final BeaconAlleleRequest alleleRequest;

  AutoValue_BeaconAlleleResponse(
      String beaconId,
      @Nullable Boolean exists,
      @Nullable BeaconError error,
      BeaconAlleleRequest alleleRequest) {
    if (beaconId == null) {
      throw new NullPointerException("Null beaconId");
    }
    this.beaconId = beaconId;
    this.exists = exists;
    this.error = error;
    if (alleleRequest == null) {
      throw new NullPointerException("Null alleleRequest");
    }
    this.alleleRequest = alleleRequest;
  }

  @Override
  public String getBeaconId() {
    return beaconId;
  }

  @Nullable
  @Override
  public Boolean getExists() {
    return exists;
  }

  @Nullable
  @Override
  public BeaconError getError() {
    return error;
  }

  @Override
  public BeaconAlleleRequest getAlleleRequest() {
    return alleleRequest;
  }

  @Override
  public String toString() {
    return "BeaconAlleleResponse{"
        + "beaconId=" + beaconId + ", "
        + "exists=" + exists + ", "
        + "error=" + error + ", "
        + "alleleRequest=" + alleleRequest
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof BeaconAlleleResponse) {
      BeaconAlleleResponse that = (BeaconAlleleResponse) o;
      return (this.beaconId.equals(that.getBeaconId()))
           && ((this.exists == null) ? (that.getExists() == null) : this.exists.equals(that.getExists()))
           && ((this.error == null) ? (that.getError() == null) : this.error.equals(that.getError()))
           && (this.alleleRequest.equals(that.getAlleleRequest()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.beaconId.hashCode();
    h *= 1000003;
    h ^= (exists == null) ? 0 : this.exists.hashCode();
    h *= 1000003;
    h ^= (error == null) ? 0 : this.error.hashCode();
    h *= 1000003;
    h ^= this.alleleRequest.hashCode();
    return h;
  }

}
