
package org.molgenis.data.elasticsearch.client.model;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_SearchHit extends SearchHit {

  private final String id;
  private final String index;

  private AutoValue_SearchHit(
      String id,
      String index) {
    this.id = id;
    this.index = index;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getIndex() {
    return index;
  }

  @Override
  public String toString() {
    return "SearchHit{"
        + "id=" + id + ", "
        + "index=" + index
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof SearchHit) {
      SearchHit that = (SearchHit) o;
      return (this.id.equals(that.getId()))
           && (this.index.equals(that.getIndex()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= this.index.hashCode();
    return h;
  }

  static final class Builder extends SearchHit.Builder {
    private String id;
    private String index;
    Builder() {
    }
    @Override
    public SearchHit.Builder setId(String id) {
      if (id == null) {
        throw new NullPointerException("Null id");
      }
      this.id = id;
      return this;
    }
    @Override
    public SearchHit.Builder setIndex(String index) {
      if (index == null) {
        throw new NullPointerException("Null index");
      }
      this.index = index;
      return this;
    }
    @Override
    public SearchHit build() {
      String missing = "";
      if (this.id == null) {
        missing += " id";
      }
      if (this.index == null) {
        missing += " index";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_SearchHit(
          this.id,
          this.index);
    }
  }

}
