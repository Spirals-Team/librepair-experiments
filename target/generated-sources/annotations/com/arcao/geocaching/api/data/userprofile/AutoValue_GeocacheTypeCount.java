
package com.arcao.geocaching.api.data.userprofile;

import com.arcao.geocaching.api.data.type.GeocacheType;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_GeocacheTypeCount extends GeocacheTypeCount {

  private final GeocacheType type;
  private final int count;

  private AutoValue_GeocacheTypeCount(
      GeocacheType type,
      int count) {
    this.type = type;
    this.count = count;
  }

  @Override
  public GeocacheType type() {
    return type;
  }

  @Override
  public int count() {
    return count;
  }

  @Override
  public String toString() {
    return "GeocacheTypeCount{"
        + "type=" + type + ", "
        + "count=" + count
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GeocacheTypeCount) {
      GeocacheTypeCount that = (GeocacheTypeCount) o;
      return (this.type.equals(that.type()))
           && (this.count == that.count());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.type.hashCode();
    h *= 1000003;
    h ^= this.count;
    return h;
  }

  private static final long serialVersionUID = -5154501054241628739L;

  static final class Builder extends GeocacheTypeCount.Builder {
    private GeocacheType type;
    private Integer count;
    Builder() {
    }
    @Override
    public GeocacheTypeCount.Builder type(GeocacheType type) {
      if (type == null) {
        throw new NullPointerException("Null type");
      }
      this.type = type;
      return this;
    }
    @Override
    public GeocacheTypeCount.Builder count(int count) {
      this.count = count;
      return this;
    }
    @Override
    public GeocacheTypeCount build() {
      String missing = "";
      if (this.type == null) {
        missing += " type";
      }
      if (this.count == null) {
        missing += " count";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_GeocacheTypeCount(
          this.type,
          this.count);
    }
  }

}
