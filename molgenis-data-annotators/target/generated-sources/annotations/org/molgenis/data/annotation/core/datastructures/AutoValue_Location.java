
package org.molgenis.data.annotation.core.datastructures;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Location extends Location {

  private final String chrom;
  private final int pos;

  AutoValue_Location(
      String chrom,
      int pos) {
    if (chrom == null) {
      throw new NullPointerException("Null chrom");
    }
    this.chrom = chrom;
    this.pos = pos;
  }

  @Override
  public String getChrom() {
    return chrom;
  }

  @Override
  public int getPos() {
    return pos;
  }

  @Override
  public String toString() {
    return "Location{"
        + "chrom=" + chrom + ", "
        + "pos=" + pos
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Location) {
      Location that = (Location) o;
      return (this.chrom.equals(that.getChrom()))
           && (this.pos == that.getPos());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.chrom.hashCode();
    h *= 1000003;
    h ^= this.pos;
    return h;
  }

}
