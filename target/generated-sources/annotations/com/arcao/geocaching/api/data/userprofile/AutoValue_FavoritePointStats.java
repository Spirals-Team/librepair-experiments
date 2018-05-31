

package com.arcao.geocaching.api.data.userprofile;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_FavoritePointStats extends FavoritePointStats {

  private AutoValue_FavoritePointStats(
 ) {
  }

  @Override
  public String toString() {
    return "FavoritePointStats{"
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof FavoritePointStats) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    return h$;
  }

  private static final long serialVersionUID = -5759425542897341813L;

  static final class Builder extends FavoritePointStats.Builder {
    Builder() {
    }
    @Override
    public FavoritePointStats build() {
      return new AutoValue_FavoritePointStats(
   );
    }
  }

}
