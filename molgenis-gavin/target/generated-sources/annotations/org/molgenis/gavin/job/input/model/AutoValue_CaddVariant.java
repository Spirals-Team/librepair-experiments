
package org.molgenis.gavin.job.input.model;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_CaddVariant extends CaddVariant {

  private final String chrom;
  private final long pos;
  private final String ref;
  private final String alt;
  private final Double rawScore;
  private final Double phred;

  AutoValue_CaddVariant(
      String chrom,
      long pos,
      String ref,
      String alt,
      @Nullable Double rawScore,
      @Nullable Double phred) {
    if (chrom == null) {
      throw new NullPointerException("Null chrom");
    }
    this.chrom = chrom;
    this.pos = pos;
    if (ref == null) {
      throw new NullPointerException("Null ref");
    }
    this.ref = ref;
    if (alt == null) {
      throw new NullPointerException("Null alt");
    }
    this.alt = alt;
    this.rawScore = rawScore;
    this.phred = phred;
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
  public String getRef() {
    return ref;
  }

  @Override
  public String getAlt() {
    return alt;
  }

  @Nullable
  @Override
  public Double getRawScore() {
    return rawScore;
  }

  @Nullable
  @Override
  public Double getPhred() {
    return phred;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof CaddVariant) {
      CaddVariant that = (CaddVariant) o;
      return (this.chrom.equals(that.getChrom()))
           && (this.pos == that.getPos())
           && (this.ref.equals(that.getRef()))
           && (this.alt.equals(that.getAlt()))
           && ((this.rawScore == null) ? (that.getRawScore() == null) : this.rawScore.equals(that.getRawScore()))
           && ((this.phred == null) ? (that.getPhred() == null) : this.phred.equals(that.getPhred()));
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
    h ^= this.ref.hashCode();
    h *= 1000003;
    h ^= this.alt.hashCode();
    h *= 1000003;
    h ^= (rawScore == null) ? 0 : this.rawScore.hashCode();
    h *= 1000003;
    h ^= (phred == null) ? 0 : this.phred.hashCode();
    return h;
  }

}
