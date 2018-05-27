
package com.arcao.geocaching.api.data.userprofile;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_GlobalStats extends GlobalStats {

  private final long accountsLogged;
  private final long activeCaches;
  private final long activeCountries;
  private final long newLogs;

  private AutoValue_GlobalStats(
      long accountsLogged,
      long activeCaches,
      long activeCountries,
      long newLogs) {
    this.accountsLogged = accountsLogged;
    this.activeCaches = activeCaches;
    this.activeCountries = activeCountries;
    this.newLogs = newLogs;
  }

  @Override
  public long accountsLogged() {
    return accountsLogged;
  }

  @Override
  public long activeCaches() {
    return activeCaches;
  }

  @Override
  public long activeCountries() {
    return activeCountries;
  }

  @Override
  public long newLogs() {
    return newLogs;
  }

  @Override
  public String toString() {
    return "GlobalStats{"
        + "accountsLogged=" + accountsLogged + ", "
        + "activeCaches=" + activeCaches + ", "
        + "activeCountries=" + activeCountries + ", "
        + "newLogs=" + newLogs
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GlobalStats) {
      GlobalStats that = (GlobalStats) o;
      return (this.accountsLogged == that.accountsLogged())
           && (this.activeCaches == that.activeCaches())
           && (this.activeCountries == that.activeCountries())
           && (this.newLogs == that.newLogs());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (this.accountsLogged >>> 32) ^ this.accountsLogged;
    h *= 1000003;
    h ^= (this.activeCaches >>> 32) ^ this.activeCaches;
    h *= 1000003;
    h ^= (this.activeCountries >>> 32) ^ this.activeCountries;
    h *= 1000003;
    h ^= (this.newLogs >>> 32) ^ this.newLogs;
    return h;
  }

  private static final long serialVersionUID = 7066712324435905861L;

  static final class Builder extends GlobalStats.Builder {
    private Long accountsLogged;
    private Long activeCaches;
    private Long activeCountries;
    private Long newLogs;
    Builder() {
    }
    @Override
    public GlobalStats.Builder accountsLogged(long accountsLogged) {
      this.accountsLogged = accountsLogged;
      return this;
    }
    @Override
    public GlobalStats.Builder activeCaches(long activeCaches) {
      this.activeCaches = activeCaches;
      return this;
    }
    @Override
    public GlobalStats.Builder activeCountries(long activeCountries) {
      this.activeCountries = activeCountries;
      return this;
    }
    @Override
    public GlobalStats.Builder newLogs(long newLogs) {
      this.newLogs = newLogs;
      return this;
    }
    @Override
    public GlobalStats build() {
      String missing = "";
      if (this.accountsLogged == null) {
        missing += " accountsLogged";
      }
      if (this.activeCaches == null) {
        missing += " activeCaches";
      }
      if (this.activeCountries == null) {
        missing += " activeCountries";
      }
      if (this.newLogs == null) {
        missing += " newLogs";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_GlobalStats(
          this.accountsLogged,
          this.activeCaches,
          this.activeCountries,
          this.newLogs);
    }
  }

}
