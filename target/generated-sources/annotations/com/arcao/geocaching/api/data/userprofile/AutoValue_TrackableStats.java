
package com.arcao.geocaching.api.data.userprofile;

import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_TrackableStats extends TrackableStats {

  private final int findCount;
  private final List<TrackableTypeCount> findTypes;
  private final int ownedCount;
  private final List<TrackableTypeCount> ownedTypes;

  private AutoValue_TrackableStats(
      int findCount,
      List<TrackableTypeCount> findTypes,
      int ownedCount,
      List<TrackableTypeCount> ownedTypes) {
    this.findCount = findCount;
    this.findTypes = findTypes;
    this.ownedCount = ownedCount;
    this.ownedTypes = ownedTypes;
  }

  @Override
  public int findCount() {
    return findCount;
  }

  @Override
  public List<TrackableTypeCount> findTypes() {
    return findTypes;
  }

  @Override
  public int ownedCount() {
    return ownedCount;
  }

  @Override
  public List<TrackableTypeCount> ownedTypes() {
    return ownedTypes;
  }

  @Override
  public String toString() {
    return "TrackableStats{"
        + "findCount=" + findCount + ", "
        + "findTypes=" + findTypes + ", "
        + "ownedCount=" + ownedCount + ", "
        + "ownedTypes=" + ownedTypes
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof TrackableStats) {
      TrackableStats that = (TrackableStats) o;
      return (this.findCount == that.findCount())
           && (this.findTypes.equals(that.findTypes()))
           && (this.ownedCount == that.ownedCount())
           && (this.ownedTypes.equals(that.ownedTypes()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.findCount;
    h *= 1000003;
    h ^= this.findTypes.hashCode();
    h *= 1000003;
    h ^= this.ownedCount;
    h *= 1000003;
    h ^= this.ownedTypes.hashCode();
    return h;
  }

  private static final long serialVersionUID = 8539963736459413400L;

  static final class Builder extends TrackableStats.Builder {
    private Integer findCount;
    private List<TrackableTypeCount> findTypes;
    private Integer ownedCount;
    private List<TrackableTypeCount> ownedTypes;
    Builder() {
    }
    @Override
    public TrackableStats.Builder findCount(int findCount) {
      this.findCount = findCount;
      return this;
    }
    @Override
    public TrackableStats.Builder findTypes(List<TrackableTypeCount> findTypes) {
      if (findTypes == null) {
        throw new NullPointerException("Null findTypes");
      }
      this.findTypes = findTypes;
      return this;
    }
    @Override
    public TrackableStats.Builder ownedCount(int ownedCount) {
      this.ownedCount = ownedCount;
      return this;
    }
    @Override
    public TrackableStats.Builder ownedTypes(List<TrackableTypeCount> ownedTypes) {
      if (ownedTypes == null) {
        throw new NullPointerException("Null ownedTypes");
      }
      this.ownedTypes = ownedTypes;
      return this;
    }
    @Override
    public TrackableStats build() {
      String missing = "";
      if (this.findCount == null) {
        missing += " findCount";
      }
      if (this.findTypes == null) {
        missing += " findTypes";
      }
      if (this.ownedCount == null) {
        missing += " ownedCount";
      }
      if (this.ownedTypes == null) {
        missing += " ownedTypes";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_TrackableStats(
          this.findCount,
          this.findTypes,
          this.ownedCount,
          this.ownedTypes);
    }
  }

}
