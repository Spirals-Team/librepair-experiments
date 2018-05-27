
package com.arcao.geocaching.api.data;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_FavoritePointResult extends FavoritePointResult {

  private final int cacheFavoritePoints;
  private final int usersFavoritePoints;

  private AutoValue_FavoritePointResult(
      int cacheFavoritePoints,
      int usersFavoritePoints) {
    this.cacheFavoritePoints = cacheFavoritePoints;
    this.usersFavoritePoints = usersFavoritePoints;
  }

  @Override
  public int cacheFavoritePoints() {
    return cacheFavoritePoints;
  }

  @Override
  public int usersFavoritePoints() {
    return usersFavoritePoints;
  }

  @Override
  public String toString() {
    return "FavoritePointResult{"
        + "cacheFavoritePoints=" + cacheFavoritePoints + ", "
        + "usersFavoritePoints=" + usersFavoritePoints
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof FavoritePointResult) {
      FavoritePointResult that = (FavoritePointResult) o;
      return (this.cacheFavoritePoints == that.cacheFavoritePoints())
           && (this.usersFavoritePoints == that.usersFavoritePoints());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.cacheFavoritePoints;
    h *= 1000003;
    h ^= this.usersFavoritePoints;
    return h;
  }

  private static final long serialVersionUID = -8463568668075962169L;

  static final class Builder extends FavoritePointResult.Builder {
    private Integer cacheFavoritePoints;
    private Integer usersFavoritePoints;
    Builder() {
    }
    @Override
    public FavoritePointResult.Builder cacheFavoritePoints(int cacheFavoritePoints) {
      this.cacheFavoritePoints = cacheFavoritePoints;
      return this;
    }
    @Override
    public FavoritePointResult.Builder usersFavoritePoints(int usersFavoritePoints) {
      this.usersFavoritePoints = usersFavoritePoints;
      return this;
    }
    @Override
    public FavoritePointResult build() {
      String missing = "";
      if (this.cacheFavoritePoints == null) {
        missing += " cacheFavoritePoints";
      }
      if (this.usersFavoritePoints == null) {
        missing += " usersFavoritePoints";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_FavoritePointResult(
          this.cacheFavoritePoints,
          this.usersFavoritePoints);
    }
  }

}
