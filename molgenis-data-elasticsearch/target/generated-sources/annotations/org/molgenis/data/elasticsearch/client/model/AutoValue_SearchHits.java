
package org.molgenis.data.elasticsearch.client.model;

import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_SearchHits extends SearchHits {

  private final long totalHits;
  private final List<SearchHit> hits;

  private AutoValue_SearchHits(
      long totalHits,
      List<SearchHit> hits) {
    this.totalHits = totalHits;
    this.hits = hits;
  }

  @Override
  public long getTotalHits() {
    return totalHits;
  }

  @Override
  public List<SearchHit> getHits() {
    return hits;
  }

  @Override
  public String toString() {
    return "SearchHits{"
        + "totalHits=" + totalHits + ", "
        + "hits=" + hits
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof SearchHits) {
      SearchHits that = (SearchHits) o;
      return (this.totalHits == that.getTotalHits())
           && (this.hits.equals(that.getHits()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (this.totalHits >>> 32) ^ this.totalHits;
    h *= 1000003;
    h ^= this.hits.hashCode();
    return h;
  }

  static final class Builder extends SearchHits.Builder {
    private Long totalHits;
    private List<SearchHit> hits;
    Builder() {
    }
    @Override
    public SearchHits.Builder setTotalHits(long totalHits) {
      this.totalHits = totalHits;
      return this;
    }
    @Override
    public SearchHits.Builder setHits(List<SearchHit> hits) {
      if (hits == null) {
        throw new NullPointerException("Null hits");
      }
      this.hits = hits;
      return this;
    }
    @Override
    public SearchHits build() {
      String missing = "";
      if (this.totalHits == null) {
        missing += " totalHits";
      }
      if (this.hits == null) {
        missing += " hits";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_SearchHits(
          this.totalHits,
          this.hits);
    }
  }

}
