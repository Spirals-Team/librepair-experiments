
package com.arcao.geocaching.api.data;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_GeocacheLimits extends GeocacheLimits {

  private final int geocacheLeft;
  private final int currentGeocacheCount;
  private final int maxGeocacheCount;

  private AutoValue_GeocacheLimits(
      int geocacheLeft,
      int currentGeocacheCount,
      int maxGeocacheCount) {
    this.geocacheLeft = geocacheLeft;
    this.currentGeocacheCount = currentGeocacheCount;
    this.maxGeocacheCount = maxGeocacheCount;
  }

  @Override
  public int geocacheLeft() {
    return geocacheLeft;
  }

  @Override
  public int currentGeocacheCount() {
    return currentGeocacheCount;
  }

  @Override
  public int maxGeocacheCount() {
    return maxGeocacheCount;
  }

  @Override
  public String toString() {
    return "GeocacheLimits{"
        + "geocacheLeft=" + geocacheLeft + ", "
        + "currentGeocacheCount=" + currentGeocacheCount + ", "
        + "maxGeocacheCount=" + maxGeocacheCount
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GeocacheLimits) {
      GeocacheLimits that = (GeocacheLimits) o;
      return (this.geocacheLeft == that.geocacheLeft())
           && (this.currentGeocacheCount == that.currentGeocacheCount())
           && (this.maxGeocacheCount == that.maxGeocacheCount());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.geocacheLeft;
    h *= 1000003;
    h ^= this.currentGeocacheCount;
    h *= 1000003;
    h ^= this.maxGeocacheCount;
    return h;
  }

  private static final long serialVersionUID = 907830786611718961L;

  static final class Builder extends GeocacheLimits.Builder {
    private Integer geocacheLeft;
    private Integer currentGeocacheCount;
    private Integer maxGeocacheCount;
    Builder() {
    }
    @Override
    public GeocacheLimits.Builder geocacheLeft(int geocacheLeft) {
      this.geocacheLeft = geocacheLeft;
      return this;
    }
    @Override
    public GeocacheLimits.Builder currentGeocacheCount(int currentGeocacheCount) {
      this.currentGeocacheCount = currentGeocacheCount;
      return this;
    }
    @Override
    public GeocacheLimits.Builder maxGeocacheCount(int maxGeocacheCount) {
      this.maxGeocacheCount = maxGeocacheCount;
      return this;
    }
    @Override
    public GeocacheLimits build() {
      String missing = "";
      if (this.geocacheLeft == null) {
        missing += " geocacheLeft";
      }
      if (this.currentGeocacheCount == null) {
        missing += " currentGeocacheCount";
      }
      if (this.maxGeocacheCount == null) {
        missing += " maxGeocacheCount";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_GeocacheLimits(
          this.geocacheLeft,
          this.currentGeocacheCount,
          this.maxGeocacheCount);
    }
  }

}
