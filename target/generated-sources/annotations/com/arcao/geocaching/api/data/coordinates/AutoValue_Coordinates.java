

package com.arcao.geocaching.api.data.coordinates;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Coordinates extends Coordinates {

  private final double latitude;
  private final double longitude;

  AutoValue_Coordinates(
      double latitude,
      double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  @Override
  public double latitude() {
    return latitude;
  }

  @Override
  public double longitude() {
    return longitude;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= (int) ((Double.doubleToLongBits(latitude) >>> 32) ^ Double.doubleToLongBits(latitude));
    h$ *= 1000003;
    h$ ^= (int) ((Double.doubleToLongBits(longitude) >>> 32) ^ Double.doubleToLongBits(longitude));
    return h$;
  }

  private static final long serialVersionUID = 1044000671539652241L;

}
