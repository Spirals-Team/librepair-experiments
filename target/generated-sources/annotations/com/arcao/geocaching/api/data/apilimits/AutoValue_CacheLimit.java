

package com.arcao.geocaching.api.data.apilimits;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_CacheLimit extends CacheLimit {

  private final long limit;
  private final long period;

  private AutoValue_CacheLimit(
      long limit,
      long period) {
    this.limit = limit;
    this.period = period;
  }

  @Override
  public long limit() {
    return limit;
  }

  @Override
  public long period() {
    return period;
  }

  @Override
  public String toString() {
    return "CacheLimit{"
         + "limit=" + limit + ", "
         + "period=" + period
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof CacheLimit) {
      CacheLimit that = (CacheLimit) o;
      return (this.limit == that.limit())
           && (this.period == that.period());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= (int) ((limit >>> 32) ^ limit);
    h$ *= 1000003;
    h$ ^= (int) ((period >>> 32) ^ period);
    return h$;
  }

  private static final long serialVersionUID = 5480568653613770776L;

  static final class Builder extends CacheLimit.Builder {
    private Long limit;
    private Long period;
    Builder() {
    }
    @Override
    public CacheLimit.Builder limit(long limit) {
      this.limit = limit;
      return this;
    }
    @Override
    public CacheLimit.Builder period(long period) {
      this.period = period;
      return this;
    }
    @Override
    public CacheLimit build() {
      String missing = "";
      if (this.limit == null) {
        missing += " limit";
      }
      if (this.period == null) {
        missing += " period";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_CacheLimit(
          this.limit,
          this.period);
    }
  }

}
