
package org.molgenis.gavin.job.input.model;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_VcfVariant extends VcfVariant {

  private final String chrom;
  private final long pos;
  private final String id;
  private final String ref;
  private final String alt;

  AutoValue_VcfVariant(
      String chrom,
      long pos,
      String id,
      String ref,
      String alt) {
    if (chrom == null) {
      throw new NullPointerException("Null chrom");
    }
    this.chrom = chrom;
    this.pos = pos;
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    if (ref == null) {
      throw new NullPointerException("Null ref");
    }
    this.ref = ref;
    if (alt == null) {
      throw new NullPointerException("Null alt");
    }
    this.alt = alt;
  }

  @Override
  public String getChrom() {
    return chrom;
  }

  @Override
  public long getPos() {
    return pos;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getRef() {
    return ref;
  }

  @Override
  public String getAlt() {
    return alt;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof VcfVariant) {
      VcfVariant that = (VcfVariant) o;
      return (this.chrom.equals(that.getChrom()))
           && (this.pos == that.getPos())
           && (this.id.equals(that.getId()))
           && (this.ref.equals(that.getRef()))
           && (this.alt.equals(that.getAlt()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.chrom.hashCode();
    h *= 1000003;
    h ^= (this.pos >>> 32) ^ this.pos;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= this.ref.hashCode();
    h *= 1000003;
    h ^= this.alt.hashCode();
    return h;
  }

}
