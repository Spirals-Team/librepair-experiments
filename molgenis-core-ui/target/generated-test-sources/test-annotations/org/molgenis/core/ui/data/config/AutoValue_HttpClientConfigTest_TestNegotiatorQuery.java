
package org.molgenis.core.ui.data.config;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_HttpClientConfigTest_TestNegotiatorQuery extends HttpClientConfigTest.TestNegotiatorQuery {

  private final String URL;
  private final String nToken;

  AutoValue_HttpClientConfigTest_TestNegotiatorQuery(
      String URL,
      String nToken) {
    if (URL == null) {
      throw new NullPointerException("Null URL");
    }
    this.URL = URL;
    if (nToken == null) {
      throw new NullPointerException("Null nToken");
    }
    this.nToken = nToken;
  }

  @Override
  public String getURL() {
    return URL;
  }

  @Override
  public String getnToken() {
    return nToken;
  }

  @Override
  public String toString() {
    return "TestNegotiatorQuery{"
        + "URL=" + URL + ", "
        + "nToken=" + nToken
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof HttpClientConfigTest.TestNegotiatorQuery) {
      HttpClientConfigTest.TestNegotiatorQuery that = (HttpClientConfigTest.TestNegotiatorQuery) o;
      return (this.URL.equals(that.getURL()))
           && (this.nToken.equals(that.getnToken()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.URL.hashCode();
    h *= 1000003;
    h ^= this.nToken.hashCode();
    return h;
  }

}
