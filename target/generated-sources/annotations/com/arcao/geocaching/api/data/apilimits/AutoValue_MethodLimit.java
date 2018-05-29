

package com.arcao.geocaching.api.data.apilimits;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_MethodLimit extends MethodLimit {

  private final int period;
  private final long limit;
  private final String methodName;
  private final boolean partnerMethod;

  private AutoValue_MethodLimit(
      int period,
      long limit,
      String methodName,
      boolean partnerMethod) {
    this.period = period;
    this.limit = limit;
    this.methodName = methodName;
    this.partnerMethod = partnerMethod;
  }

  @Override
  public int period() {
    return period;
  }

  @Override
  public long limit() {
    return limit;
  }

  @Override
  public String methodName() {
    return methodName;
  }

  @Override
  public boolean partnerMethod() {
    return partnerMethod;
  }

  @Override
  public String toString() {
    return "MethodLimit{"
         + "period=" + period + ", "
         + "limit=" + limit + ", "
         + "methodName=" + methodName + ", "
         + "partnerMethod=" + partnerMethod
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof MethodLimit) {
      MethodLimit that = (MethodLimit) o;
      return (this.period == that.period())
           && (this.limit == that.limit())
           && (this.methodName.equals(that.methodName()))
           && (this.partnerMethod == that.partnerMethod());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= period;
    h$ *= 1000003;
    h$ ^= (int) ((limit >>> 32) ^ limit);
    h$ *= 1000003;
    h$ ^= methodName.hashCode();
    h$ *= 1000003;
    h$ ^= partnerMethod ? 1231 : 1237;
    return h$;
  }

  private static final long serialVersionUID = 3914171011300602155L;

  static final class Builder extends MethodLimit.Builder {
    private Integer period;
    private Long limit;
    private String methodName;
    private Boolean partnerMethod;
    Builder() {
    }
    @Override
    public MethodLimit.Builder period(int period) {
      this.period = period;
      return this;
    }
    @Override
    public MethodLimit.Builder limit(long limit) {
      this.limit = limit;
      return this;
    }
    @Override
    public MethodLimit.Builder methodName(String methodName) {
      if (methodName == null) {
        throw new NullPointerException("Null methodName");
      }
      this.methodName = methodName;
      return this;
    }
    @Override
    public MethodLimit.Builder partnerMethod(boolean partnerMethod) {
      this.partnerMethod = partnerMethod;
      return this;
    }
    @Override
    public MethodLimit build() {
      String missing = "";
      if (this.period == null) {
        missing += " period";
      }
      if (this.limit == null) {
        missing += " limit";
      }
      if (this.methodName == null) {
        missing += " methodName";
      }
      if (this.partnerMethod == null) {
        missing += " partnerMethod";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_MethodLimit(
          this.period,
          this.limit,
          this.methodName,
          this.partnerMethod);
    }
  }

}
