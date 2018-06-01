
package org.molgenis.dataexplorer.negotiator;

import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_NegotiatorQuery extends NegotiatorQuery {

  private final String URL;
  private final List<Collection> collections;
  private final String humanReadable;
  private final String nToken;

  AutoValue_NegotiatorQuery(
      String URL,
      List<Collection> collections,
      String humanReadable,
      @Nullable String nToken) {
    if (URL == null) {
      throw new NullPointerException("Null URL");
    }
    this.URL = URL;
    if (collections == null) {
      throw new NullPointerException("Null collections");
    }
    this.collections = collections;
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
  public List<Collection> getCollections() {
    return collections;
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
    return "NegotiatorQuery{"
        + "URL=" + URL + ", "
        + "collections=" + collections + ", "
        + "humanReadable=" + humanReadable + ", "
        + "nToken=" + nToken
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof NegotiatorQuery) {
      NegotiatorQuery that = (NegotiatorQuery) o;
      return (this.URL.equals(that.getURL()))
           && (this.collections.equals(that.getCollections()))
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
    h ^= this.collections.hashCode();
    h *= 1000003;
    h ^= this.humanReadable.hashCode();
    h *= 1000003;
    h ^= (nToken == null) ? 0 : this.nToken.hashCode();
    return h;
  }

}
