

package com.arcao.geocaching.api.data.apilimits;

import com.arcao.geocaching.api.data.type.MemberType;
import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_ApiLimits extends ApiLimits {

  private final List<CacheLimit> cacheLimits;
  private final boolean enforceCacheLimits;
  private final boolean enforceLiteCacheLimits;
  private final boolean enforceMethodLimits;
  private final MemberType forMembershipType;
  private final String licenseKey;
  private final List<CacheLimit> liteCacheLimits;
  private final long maxCallsbyIPIn1Minute;
  private final List<MethodLimit> methodLimits;
  private final boolean restrictByIp;
  private final boolean validateIpCounts;

  private AutoValue_ApiLimits(
      List<CacheLimit> cacheLimits,
      boolean enforceCacheLimits,
      boolean enforceLiteCacheLimits,
      boolean enforceMethodLimits,
      MemberType forMembershipType,
      String licenseKey,
      List<CacheLimit> liteCacheLimits,
      long maxCallsbyIPIn1Minute,
      List<MethodLimit> methodLimits,
      boolean restrictByIp,
      boolean validateIpCounts) {
    this.cacheLimits = cacheLimits;
    this.enforceCacheLimits = enforceCacheLimits;
    this.enforceLiteCacheLimits = enforceLiteCacheLimits;
    this.enforceMethodLimits = enforceMethodLimits;
    this.forMembershipType = forMembershipType;
    this.licenseKey = licenseKey;
    this.liteCacheLimits = liteCacheLimits;
    this.maxCallsbyIPIn1Minute = maxCallsbyIPIn1Minute;
    this.methodLimits = methodLimits;
    this.restrictByIp = restrictByIp;
    this.validateIpCounts = validateIpCounts;
  }

  @Override
  public List<CacheLimit> cacheLimits() {
    return cacheLimits;
  }

  @Override
  public boolean enforceCacheLimits() {
    return enforceCacheLimits;
  }

  @Override
  public boolean enforceLiteCacheLimits() {
    return enforceLiteCacheLimits;
  }

  @Override
  public boolean enforceMethodLimits() {
    return enforceMethodLimits;
  }

  @Override
  public MemberType forMembershipType() {
    return forMembershipType;
  }

  @Override
  public String licenseKey() {
    return licenseKey;
  }

  @Override
  public List<CacheLimit> liteCacheLimits() {
    return liteCacheLimits;
  }

  @Override
  public long maxCallsbyIPIn1Minute() {
    return maxCallsbyIPIn1Minute;
  }

  @Override
  public List<MethodLimit> methodLimits() {
    return methodLimits;
  }

  @Override
  public boolean restrictByIp() {
    return restrictByIp;
  }

  @Override
  public boolean validateIpCounts() {
    return validateIpCounts;
  }

  @Override
  public String toString() {
    return "ApiLimits{"
         + "cacheLimits=" + cacheLimits + ", "
         + "enforceCacheLimits=" + enforceCacheLimits + ", "
         + "enforceLiteCacheLimits=" + enforceLiteCacheLimits + ", "
         + "enforceMethodLimits=" + enforceMethodLimits + ", "
         + "forMembershipType=" + forMembershipType + ", "
         + "licenseKey=" + licenseKey + ", "
         + "liteCacheLimits=" + liteCacheLimits + ", "
         + "maxCallsbyIPIn1Minute=" + maxCallsbyIPIn1Minute + ", "
         + "methodLimits=" + methodLimits + ", "
         + "restrictByIp=" + restrictByIp + ", "
         + "validateIpCounts=" + validateIpCounts
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof ApiLimits) {
      ApiLimits that = (ApiLimits) o;
      return (this.cacheLimits.equals(that.cacheLimits()))
           && (this.enforceCacheLimits == that.enforceCacheLimits())
           && (this.enforceLiteCacheLimits == that.enforceLiteCacheLimits())
           && (this.enforceMethodLimits == that.enforceMethodLimits())
           && (this.forMembershipType.equals(that.forMembershipType()))
           && (this.licenseKey.equals(that.licenseKey()))
           && (this.liteCacheLimits.equals(that.liteCacheLimits()))
           && (this.maxCallsbyIPIn1Minute == that.maxCallsbyIPIn1Minute())
           && (this.methodLimits.equals(that.methodLimits()))
           && (this.restrictByIp == that.restrictByIp())
           && (this.validateIpCounts == that.validateIpCounts());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= cacheLimits.hashCode();
    h$ *= 1000003;
    h$ ^= enforceCacheLimits ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= enforceLiteCacheLimits ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= enforceMethodLimits ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= forMembershipType.hashCode();
    h$ *= 1000003;
    h$ ^= licenseKey.hashCode();
    h$ *= 1000003;
    h$ ^= liteCacheLimits.hashCode();
    h$ *= 1000003;
    h$ ^= (int) ((maxCallsbyIPIn1Minute >>> 32) ^ maxCallsbyIPIn1Minute);
    h$ *= 1000003;
    h$ ^= methodLimits.hashCode();
    h$ *= 1000003;
    h$ ^= restrictByIp ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= validateIpCounts ? 1231 : 1237;
    return h$;
  }

  private static final long serialVersionUID = -3608995080972521881L;

  static final class Builder extends ApiLimits.Builder {
    private List<CacheLimit> cacheLimits;
    private Boolean enforceCacheLimits;
    private Boolean enforceLiteCacheLimits;
    private Boolean enforceMethodLimits;
    private MemberType forMembershipType;
    private String licenseKey;
    private List<CacheLimit> liteCacheLimits;
    private Long maxCallsbyIPIn1Minute;
    private List<MethodLimit> methodLimits;
    private Boolean restrictByIp;
    private Boolean validateIpCounts;
    Builder() {
    }
    @Override
    public ApiLimits.Builder cacheLimits(List<CacheLimit> cacheLimits) {
      if (cacheLimits == null) {
        throw new NullPointerException("Null cacheLimits");
      }
      this.cacheLimits = cacheLimits;
      return this;
    }
    @Override
    public ApiLimits.Builder enforceCacheLimits(boolean enforceCacheLimits) {
      this.enforceCacheLimits = enforceCacheLimits;
      return this;
    }
    @Override
    public ApiLimits.Builder enforceLiteCacheLimits(boolean enforceLiteCacheLimits) {
      this.enforceLiteCacheLimits = enforceLiteCacheLimits;
      return this;
    }
    @Override
    public ApiLimits.Builder enforceMethodLimits(boolean enforceMethodLimits) {
      this.enforceMethodLimits = enforceMethodLimits;
      return this;
    }
    @Override
    public ApiLimits.Builder forMembershipType(MemberType forMembershipType) {
      if (forMembershipType == null) {
        throw new NullPointerException("Null forMembershipType");
      }
      this.forMembershipType = forMembershipType;
      return this;
    }
    @Override
    public ApiLimits.Builder licenseKey(String licenseKey) {
      if (licenseKey == null) {
        throw new NullPointerException("Null licenseKey");
      }
      this.licenseKey = licenseKey;
      return this;
    }
    @Override
    public ApiLimits.Builder liteCacheLimits(List<CacheLimit> liteCacheLimits) {
      if (liteCacheLimits == null) {
        throw new NullPointerException("Null liteCacheLimits");
      }
      this.liteCacheLimits = liteCacheLimits;
      return this;
    }
    @Override
    public ApiLimits.Builder maxCallsbyIPIn1Minute(long maxCallsbyIPIn1Minute) {
      this.maxCallsbyIPIn1Minute = maxCallsbyIPIn1Minute;
      return this;
    }
    @Override
    public ApiLimits.Builder methodLimits(List<MethodLimit> methodLimits) {
      if (methodLimits == null) {
        throw new NullPointerException("Null methodLimits");
      }
      this.methodLimits = methodLimits;
      return this;
    }
    @Override
    public ApiLimits.Builder restrictByIp(boolean restrictByIp) {
      this.restrictByIp = restrictByIp;
      return this;
    }
    @Override
    public ApiLimits.Builder validateIpCounts(boolean validateIpCounts) {
      this.validateIpCounts = validateIpCounts;
      return this;
    }
    @Override
    public ApiLimits build() {
      String missing = "";
      if (this.cacheLimits == null) {
        missing += " cacheLimits";
      }
      if (this.enforceCacheLimits == null) {
        missing += " enforceCacheLimits";
      }
      if (this.enforceLiteCacheLimits == null) {
        missing += " enforceLiteCacheLimits";
      }
      if (this.enforceMethodLimits == null) {
        missing += " enforceMethodLimits";
      }
      if (this.forMembershipType == null) {
        missing += " forMembershipType";
      }
      if (this.licenseKey == null) {
        missing += " licenseKey";
      }
      if (this.liteCacheLimits == null) {
        missing += " liteCacheLimits";
      }
      if (this.maxCallsbyIPIn1Minute == null) {
        missing += " maxCallsbyIPIn1Minute";
      }
      if (this.methodLimits == null) {
        missing += " methodLimits";
      }
      if (this.restrictByIp == null) {
        missing += " restrictByIp";
      }
      if (this.validateIpCounts == null) {
        missing += " validateIpCounts";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_ApiLimits(
          this.cacheLimits,
          this.enforceCacheLimits,
          this.enforceLiteCacheLimits,
          this.enforceMethodLimits,
          this.forMembershipType,
          this.licenseKey,
          this.liteCacheLimits,
          this.maxCallsbyIPIn1Minute,
          this.methodLimits,
          this.restrictByIp,
          this.validateIpCounts);
    }
  }

}
