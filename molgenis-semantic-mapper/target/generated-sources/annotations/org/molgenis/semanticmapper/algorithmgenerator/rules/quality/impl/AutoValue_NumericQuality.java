
package org.molgenis.semanticmapper.algorithmgenerator.rules.quality.impl;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_NumericQuality extends NumericQuality {

  private final Double qualityIndicator;

  AutoValue_NumericQuality(
      Double qualityIndicator) {
    if (qualityIndicator == null) {
      throw new NullPointerException("Null qualityIndicator");
    }
    this.qualityIndicator = qualityIndicator;
  }

  @Override
  public Double getQualityIndicator() {
    return qualityIndicator;
  }

  @Override
  public String toString() {
    return "NumericQuality{"
        + "qualityIndicator=" + qualityIndicator
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof NumericQuality) {
      NumericQuality that = (NumericQuality) o;
      return (this.qualityIndicator.equals(that.getQualityIndicator()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.qualityIndicator.hashCode();
    return h;
  }

}
