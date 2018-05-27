
package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.type.GeocacheType;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_GeocacheStatus extends GeocacheStatus {

  private final boolean archived;
  private final boolean available;
  private final String cacheCode;
  private final String cacheName;
  private final GeocacheType cacheType;
  private final boolean premium;
  private final int trackableCount;

  private AutoValue_GeocacheStatus(
      boolean archived,
      boolean available,
      String cacheCode,
      String cacheName,
      GeocacheType cacheType,
      boolean premium,
      int trackableCount) {
    this.archived = archived;
    this.available = available;
    this.cacheCode = cacheCode;
    this.cacheName = cacheName;
    this.cacheType = cacheType;
    this.premium = premium;
    this.trackableCount = trackableCount;
  }

  @Override
  public boolean archived() {
    return archived;
  }

  @Override
  public boolean available() {
    return available;
  }

  @Override
  public String cacheCode() {
    return cacheCode;
  }

  @Override
  public String cacheName() {
    return cacheName;
  }

  @Override
  public GeocacheType cacheType() {
    return cacheType;
  }

  @Override
  public boolean premium() {
    return premium;
  }

  @Override
  public int trackableCount() {
    return trackableCount;
  }

  @Override
  public String toString() {
    return "GeocacheStatus{"
        + "archived=" + archived + ", "
        + "available=" + available + ", "
        + "cacheCode=" + cacheCode + ", "
        + "cacheName=" + cacheName + ", "
        + "cacheType=" + cacheType + ", "
        + "premium=" + premium + ", "
        + "trackableCount=" + trackableCount
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GeocacheStatus) {
      GeocacheStatus that = (GeocacheStatus) o;
      return (this.archived == that.archived())
           && (this.available == that.available())
           && (this.cacheCode.equals(that.cacheCode()))
           && (this.cacheName.equals(that.cacheName()))
           && (this.cacheType.equals(that.cacheType()))
           && (this.premium == that.premium())
           && (this.trackableCount == that.trackableCount());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.archived ? 1231 : 1237;
    h *= 1000003;
    h ^= this.available ? 1231 : 1237;
    h *= 1000003;
    h ^= this.cacheCode.hashCode();
    h *= 1000003;
    h ^= this.cacheName.hashCode();
    h *= 1000003;
    h ^= this.cacheType.hashCode();
    h *= 1000003;
    h ^= this.premium ? 1231 : 1237;
    h *= 1000003;
    h ^= this.trackableCount;
    return h;
  }

  private static final long serialVersionUID = -7261546635689151998L;

  static final class Builder extends GeocacheStatus.Builder {
    private Boolean archived;
    private Boolean available;
    private String cacheCode;
    private String cacheName;
    private GeocacheType cacheType;
    private Boolean premium;
    private Integer trackableCount;
    Builder() {
    }
    @Override
    public GeocacheStatus.Builder archived(boolean archived) {
      this.archived = archived;
      return this;
    }
    @Override
    public GeocacheStatus.Builder available(boolean available) {
      this.available = available;
      return this;
    }
    @Override
    public GeocacheStatus.Builder cacheCode(String cacheCode) {
      if (cacheCode == null) {
        throw new NullPointerException("Null cacheCode");
      }
      this.cacheCode = cacheCode;
      return this;
    }
    @Override
    public GeocacheStatus.Builder cacheName(String cacheName) {
      if (cacheName == null) {
        throw new NullPointerException("Null cacheName");
      }
      this.cacheName = cacheName;
      return this;
    }
    @Override
    public GeocacheStatus.Builder cacheType(GeocacheType cacheType) {
      if (cacheType == null) {
        throw new NullPointerException("Null cacheType");
      }
      this.cacheType = cacheType;
      return this;
    }
    @Override
    public GeocacheStatus.Builder premium(boolean premium) {
      this.premium = premium;
      return this;
    }
    @Override
    public GeocacheStatus.Builder trackableCount(int trackableCount) {
      this.trackableCount = trackableCount;
      return this;
    }
    @Override
    public GeocacheStatus build() {
      String missing = "";
      if (this.archived == null) {
        missing += " archived";
      }
      if (this.available == null) {
        missing += " available";
      }
      if (this.cacheCode == null) {
        missing += " cacheCode";
      }
      if (this.cacheName == null) {
        missing += " cacheName";
      }
      if (this.cacheType == null) {
        missing += " cacheType";
      }
      if (this.premium == null) {
        missing += " premium";
      }
      if (this.trackableCount == null) {
        missing += " trackableCount";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_GeocacheStatus(
          this.archived,
          this.available,
          this.cacheCode,
          this.cacheName,
          this.cacheType,
          this.premium,
          this.trackableCount);
    }
  }

}
