
package org.molgenis.dataexplorer.negotiator;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_NegotiatorRequest extends NegotiatorRequest {

  private final String URL;
  private final String entityId;
  private final String rsql;
  private final String humanReadable;
  private final String nToken;

  AutoValue_NegotiatorRequest(
      String URL,
      String entityId,
      String rsql,
      String humanReadable,
      @Nullable String nToken) {
    if (URL == null) {
      throw new NullPointerException("Null URL");
    }
    this.URL = URL;
    if (entityId == null) {
      throw new NullPointerException("Null entityId");
    }
    this.entityId = entityId;
    if (rsql == null) {
      throw new NullPointerException("Null rsql");
    }
    this.rsql = rsql;
    if (humanReadable == null) {
      throw new NullPointerException("Null humanReadable");
    }
    this.humanReadable = humanReadable;
    this.nToken = nToken;
  }

  @Override
  public String getURL() {
    return URL;
  }

  @Override
  public String getEntityId() {
    return entityId;
  }

  @Override
  public String getRsql() {
    return rsql;
  }

  @Override
  public String getHumanReadable() {
    return humanReadable;
  }

  @Nullable
  @Override
  public String getnToken() {
    return nToken;
  }

  @Override
  public String toString() {
    return "NegotiatorRequest{"
        + "URL=" + URL + ", "
        + "entityId=" + entityId + ", "
        + "rsql=" + rsql + ", "
        + "humanReadable=" + humanReadable + ", "
        + "nToken=" + nToken
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof NegotiatorRequest) {
      NegotiatorRequest that = (NegotiatorRequest) o;
      return (this.URL.equals(that.getURL()))
           && (this.entityId.equals(that.getEntityId()))
           && (this.rsql.equals(that.getRsql()))
           && (this.humanReadable.equals(that.getHumanReadable()))
           && ((this.nToken == null) ? (that.getnToken() == null) : this.nToken.equals(that.getnToken()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.URL.hashCode();
    h *= 1000003;
    h ^= this.entityId.hashCode();
    h *= 1000003;
    h ^= this.rsql.hashCode();
    h *= 1000003;
    h ^= this.humanReadable.hashCode();
    h *= 1000003;
    h ^= (nToken == null) ? 0 : this.nToken.hashCode();
    return h;
  }

}
