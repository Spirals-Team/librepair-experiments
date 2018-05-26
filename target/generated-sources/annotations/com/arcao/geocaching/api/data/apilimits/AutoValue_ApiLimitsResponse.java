
package com.arcao.geocaching.api.data.apilimits;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_ApiLimitsResponse extends ApiLimitsResponse {

  private final ApiLimits apiLimits;
  private final MaxPerPage maxPerPage;

  AutoValue_ApiLimitsResponse(
      ApiLimits apiLimits,
      MaxPerPage maxPerPage) {
    if (apiLimits == null) {
      throw new NullPointerException("Null apiLimits");
    }
    this.apiLimits = apiLimits;
    if (maxPerPage == null) {
      throw new NullPointerException("Null maxPerPage");
    }
    this.maxPerPage = maxPerPage;
  }

  @Override
  public ApiLimits apiLimits() {
    return apiLimits;
  }

  @Override
  public MaxPerPage maxPerPage() {
    return maxPerPage;
  }

  @Override
  public String toString() {
    return "ApiLimitsResponse{"
        + "apiLimits=" + apiLimits + ", "
        + "maxPerPage=" + maxPerPage
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof ApiLimitsResponse) {
      ApiLimitsResponse that = (ApiLimitsResponse) o;
      return (this.apiLimits.equals(that.apiLimits()))
           && (this.maxPerPage.equals(that.maxPerPage()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.apiLimits.hashCode();
    h *= 1000003;
    h ^= this.maxPerPage.hashCode();
    return h;
  }

  private static final long serialVersionUID = 2753514511831397947L;

}
