
package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.type.GeocacheType;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_FavoritedGeocache extends FavoritedGeocache {

  private final String cacheCode;
  private final String cacheTitle;
  private final GeocacheType geocacheType;

  private AutoValue_FavoritedGeocache(
      String cacheCode,
      String cacheTitle,
      GeocacheType geocacheType) {
    this.cacheCode = cacheCode;
    this.cacheTitle = cacheTitle;
    this.geocacheType = geocacheType;
  }

  @Override
  public String cacheCode() {
    return cacheCode;
  }

  @Override
  public String cacheTitle() {
    return cacheTitle;
  }

  @Override
  public GeocacheType geocacheType() {
    return geocacheType;
  }

  @Override
  public String toString() {
    return "FavoritedGeocache{"
        + "cacheCode=" + cacheCode + ", "
        + "cacheTitle=" + cacheTitle + ", "
        + "geocacheType=" + geocacheType
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof FavoritedGeocache) {
      FavoritedGeocache that = (FavoritedGeocache) o;
      return (this.cacheCode.equals(that.cacheCode()))
           && (this.cacheTitle.equals(that.cacheTitle()))
           && (this.geocacheType.equals(that.geocacheType()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.cacheCode.hashCode();
    h *= 1000003;
    h ^= this.cacheTitle.hashCode();
    h *= 1000003;
    h ^= this.geocacheType.hashCode();
    return h;
  }

  private static final long serialVersionUID = 4915196892471324672L;

  static final class Builder extends FavoritedGeocache.Builder {
    private String cacheCode;
    private String cacheTitle;
    private GeocacheType geocacheType;
    Builder() {
    }
    @Override
    public FavoritedGeocache.Builder cacheCode(String cacheCode) {
      if (cacheCode == null) {
        throw new NullPointerException("Null cacheCode");
      }
      this.cacheCode = cacheCode;
      return this;
    }
    @Override
    public FavoritedGeocache.Builder cacheTitle(String cacheTitle) {
      if (cacheTitle == null) {
        throw new NullPointerException("Null cacheTitle");
      }
      this.cacheTitle = cacheTitle;
      return this;
    }
    @Override
    public FavoritedGeocache.Builder geocacheType(GeocacheType geocacheType) {
      if (geocacheType == null) {
        throw new NullPointerException("Null geocacheType");
      }
      this.geocacheType = geocacheType;
      return this;
    }
    @Override
    public FavoritedGeocache build() {
      String missing = "";
      if (this.cacheCode == null) {
        missing += " cacheCode";
      }
      if (this.cacheTitle == null) {
        missing += " cacheTitle";
      }
      if (this.geocacheType == null) {
        missing += " geocacheType";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_FavoritedGeocache(
          this.cacheCode,
          this.cacheTitle,
          this.geocacheType);
    }
  }

}
