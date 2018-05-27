
package com.arcao.geocaching.api.data.userprofile;

import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_GeocacheStats extends GeocacheStats {

  private final int findCount;
  private final List<GeocacheTypeCount> findTypes;
  private final int hideCount;
  private final List<GeocacheTypeCount> hideTypes;

  private AutoValue_GeocacheStats(
      int findCount,
      List<GeocacheTypeCount> findTypes,
      int hideCount,
      List<GeocacheTypeCount> hideTypes) {
    this.findCount = findCount;
    this.findTypes = findTypes;
    this.hideCount = hideCount;
    this.hideTypes = hideTypes;
  }

  @Override
  public int findCount() {
    return findCount;
  }

  @Override
  public List<GeocacheTypeCount> findTypes() {
    return findTypes;
  }

  @Override
  public int hideCount() {
    return hideCount;
  }

  @Override
  public List<GeocacheTypeCount> hideTypes() {
    return hideTypes;
  }

  @Override
  public String toString() {
    return "GeocacheStats{"
        + "findCount=" + findCount + ", "
        + "findTypes=" + findTypes + ", "
        + "hideCount=" + hideCount + ", "
        + "hideTypes=" + hideTypes
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GeocacheStats) {
      GeocacheStats that = (GeocacheStats) o;
      return (this.findCount == that.findCount())
           && (this.findTypes.equals(that.findTypes()))
           && (this.hideCount == that.hideCount())
           && (this.hideTypes.equals(that.hideTypes()));
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
    h ^= this.hideCount;
    h *= 1000003;
    h ^= this.hideTypes.hashCode();
    return h;
  }

  private static final long serialVersionUID = -3901690388175569693L;

  static final class Builder extends GeocacheStats.Builder {
    private Integer findCount;
    private List<GeocacheTypeCount> findTypes;
    private Integer hideCount;
    private List<GeocacheTypeCount> hideTypes;
    Builder() {
    }
    @Override
    public GeocacheStats.Builder findCount(int findCount) {
      this.findCount = findCount;
      return this;
    }
    @Override
    public GeocacheStats.Builder findTypes(List<GeocacheTypeCount> findTypes) {
      if (findTypes == null) {
        throw new NullPointerException("Null findTypes");
      }
      this.findTypes = findTypes;
      return this;
    }
    @Override
    public GeocacheStats.Builder hideCount(int hideCount) {
      this.hideCount = hideCount;
      return this;
    }
    @Override
    public GeocacheStats.Builder hideTypes(List<GeocacheTypeCount> hideTypes) {
      if (hideTypes == null) {
        throw new NullPointerException("Null hideTypes");
      }
      this.hideTypes = hideTypes;
      return this;
    }
    @Override
    public GeocacheStats build() {
      String missing = "";
      if (this.findCount == null) {
        missing += " findCount";
      }
      if (this.findTypes == null) {
        missing += " findTypes";
      }
      if (this.hideCount == null) {
        missing += " hideCount";
      }
      if (this.hideTypes == null) {
        missing += " hideTypes";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_GeocacheStats(
          this.findCount,
          this.findTypes,
          this.hideCount,
          this.hideTypes);
    }
  }

}
