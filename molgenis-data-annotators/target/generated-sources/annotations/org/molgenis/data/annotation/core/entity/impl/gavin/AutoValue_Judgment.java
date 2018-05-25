
package org.molgenis.data.annotation.core.entity.impl.gavin;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Judgment extends Judgment {

  private final Judgment.Classification classification;
  private final Judgment.Method confidence;
  private final String gene;
  private final String reason;

  AutoValue_Judgment(
      Judgment.Classification classification,
      Judgment.Method confidence,
      @Nullable String gene,
      String reason) {
    if (classification == null) {
      throw new NullPointerException("Null classification");
    }
    this.classification = classification;
    if (confidence == null) {
      throw new NullPointerException("Null confidence");
    }
    this.confidence = confidence;
    this.gene = gene;
    if (reason == null) {
      throw new NullPointerException("Null reason");
    }
    this.reason = reason;
  }

  @Override
  public Judgment.Classification getClassification() {
    return classification;
  }

  @Override
  public Judgment.Method getConfidence() {
    return confidence;
  }

  @Nullable
  @Override
  public String getGene() {
    return gene;
  }

  @Override
  public String getReason() {
    return reason;
  }

  @Override
  public String toString() {
    return "Judgment{"
        + "classification=" + classification + ", "
        + "confidence=" + confidence + ", "
        + "gene=" + gene + ", "
        + "reason=" + reason
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Judgment) {
      Judgment that = (Judgment) o;
      return (this.classification.equals(that.getClassification()))
           && (this.confidence.equals(that.getConfidence()))
           && ((this.gene == null) ? (that.getGene() == null) : this.gene.equals(that.getGene()))
           && (this.reason.equals(that.getReason()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.classification.hashCode();
    h *= 1000003;
    h ^= this.confidence.hashCode();
    h *= 1000003;
    h ^= (gene == null) ? 0 : this.gene.hashCode();
    h *= 1000003;
    h ^= this.reason.hashCode();
    return h;
  }

}
