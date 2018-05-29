

package com.arcao.geocaching.api.data.userprofile;

import javax.annotation.Generated;
import org.jetbrains.annotations.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_TrackableTypeCount extends TrackableTypeCount {

  private final long id;
  private final String name;
  private final String iconUrl;
  private final int count;

  private AutoValue_TrackableTypeCount(
      long id,
      String name,
      @Nullable String iconUrl,
      int count) {
    this.id = id;
    this.name = name;
    this.iconUrl = iconUrl;
    this.count = count;
  }

  @Override
  public long id() {
    return id;
  }

  @Override
  public String name() {
    return name;
  }

  @Nullable
  @Override
  public String iconUrl() {
    return iconUrl;
  }

  @Override
  public int count() {
    return count;
  }

  @Override
  public String toString() {
    return "TrackableTypeCount{"
         + "id=" + id + ", "
         + "name=" + name + ", "
         + "iconUrl=" + iconUrl + ", "
         + "count=" + count
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof TrackableTypeCount) {
      TrackableTypeCount that = (TrackableTypeCount) o;
      return (this.id == that.id())
           && (this.name.equals(that.name()))
           && ((this.iconUrl == null) ? (that.iconUrl() == null) : this.iconUrl.equals(that.iconUrl()))
           && (this.count == that.count());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= (int) ((id >>> 32) ^ id);
    h$ *= 1000003;
    h$ ^= name.hashCode();
    h$ *= 1000003;
    h$ ^= (iconUrl == null) ? 0 : iconUrl.hashCode();
    h$ *= 1000003;
    h$ ^= count;
    return h$;
  }

  static final class Builder extends TrackableTypeCount.Builder {
    private Long id;
    private String name;
    private String iconUrl;
    private Integer count;
    Builder() {
    }
    @Override
    public TrackableTypeCount.Builder id(long id) {
      this.id = id;
      return this;
    }
    @Override
    public TrackableTypeCount.Builder name(String name) {
      if (name == null) {
        throw new NullPointerException("Null name");
      }
      this.name = name;
      return this;
    }
    @Override
    public TrackableTypeCount.Builder iconUrl(@Nullable String iconUrl) {
      this.iconUrl = iconUrl;
      return this;
    }
    @Override
    public TrackableTypeCount.Builder count(int count) {
      this.count = count;
      return this;
    }
    @Override
    public TrackableTypeCount build() {
      String missing = "";
      if (this.id == null) {
        missing += " id";
      }
      if (this.name == null) {
        missing += " name";
      }
      if (this.count == null) {
        missing += " count";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_TrackableTypeCount(
          this.id,
          this.name,
          this.iconUrl,
          this.count);
    }
  }

}
