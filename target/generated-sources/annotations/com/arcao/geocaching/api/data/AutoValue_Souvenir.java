

package com.arcao.geocaching.api.data;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Souvenir extends Souvenir {

  private AutoValue_Souvenir(
 ) {
  }

  @Override
  public String toString() {
    return "Souvenir{"
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Souvenir) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    return h$;
  }

  private static final long serialVersionUID = -1185578859898344877L;

  static final class Builder extends Souvenir.Builder {
    Builder() {
    }
    @Override
    public Souvenir build() {
      return new AutoValue_Souvenir(
   );
    }
  }

}
