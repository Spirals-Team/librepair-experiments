
package org.molgenis.data.annotation.core.entity.impl.gavin;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_GavinThresholds extends GavinThresholds {

  private final Double pathoMAFThreshold;
  private final Double meanPathogenicCADDScore;
  private final Double meanPopulationCADDScore;
  private final Double spec95thPerCADDThreshold;
  private final Double sens95thPerCADDThreshold;
  private final Category category;

  AutoValue_GavinThresholds(
      @Nullable Double pathoMAFThreshold,
      @Nullable Double meanPathogenicCADDScore,
      @Nullable Double meanPopulationCADDScore,
      @Nullable Double spec95thPerCADDThreshold,
      @Nullable Double sens95thPerCADDThreshold,
      Category category) {
    this.pathoMAFThreshold = pathoMAFThreshold;
    this.meanPathogenicCADDScore = meanPathogenicCADDScore;
    this.meanPopulationCADDScore = meanPopulationCADDScore;
    this.spec95thPerCADDThreshold = spec95thPerCADDThreshold;
    this.sens95thPerCADDThreshold = sens95thPerCADDThreshold;
    if (category == null) {
      throw new NullPointerException("Null category");
    }
    this.category = category;
  }

  @Nullable
  @Override
  public Double getPathoMAFThreshold() {
    return pathoMAFThreshold;
  }

  @Nullable
  @Override
  public Double getMeanPathogenicCADDScore() {
    return meanPathogenicCADDScore;
  }

  @Nullable
  @Override
  public Double getMeanPopulationCADDScore() {
    return meanPopulationCADDScore;
  }

  @Nullable
  @Override
  public Double getSpec95thPerCADDThreshold() {
    return spec95thPerCADDThreshold;
  }

  @Nullable
  @Override
  public Double getSens95thPerCADDThreshold() {
    return sens95thPerCADDThreshold;
  }

  @Override
  public Category getCategory() {
    return category;
  }

  @Override
  public String toString() {
    return "GavinThresholds{"
        + "pathoMAFThreshold=" + pathoMAFThreshold + ", "
        + "meanPathogenicCADDScore=" + meanPathogenicCADDScore + ", "
        + "meanPopulationCADDScore=" + meanPopulationCADDScore + ", "
        + "spec95thPerCADDThreshold=" + spec95thPerCADDThreshold + ", "
        + "sens95thPerCADDThreshold=" + sens95thPerCADDThreshold + ", "
        + "category=" + category
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GavinThresholds) {
      GavinThresholds that = (GavinThresholds) o;
      return ((this.pathoMAFThreshold == null) ? (that.getPathoMAFThreshold() == null) : this.pathoMAFThreshold.equals(that.getPathoMAFThreshold()))
           && ((this.meanPathogenicCADDScore == null) ? (that.getMeanPathogenicCADDScore() == null) : this.meanPathogenicCADDScore.equals(that.getMeanPathogenicCADDScore()))
           && ((this.meanPopulationCADDScore == null) ? (that.getMeanPopulationCADDScore() == null) : this.meanPopulationCADDScore.equals(that.getMeanPopulationCADDScore()))
           && ((this.spec95thPerCADDThreshold == null) ? (that.getSpec95thPerCADDThreshold() == null) : this.spec95thPerCADDThreshold.equals(that.getSpec95thPerCADDThreshold()))
           && ((this.sens95thPerCADDThreshold == null) ? (that.getSens95thPerCADDThreshold() == null) : this.sens95thPerCADDThreshold.equals(that.getSens95thPerCADDThreshold()))
           && (this.category.equals(that.getCategory()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (pathoMAFThreshold == null) ? 0 : this.pathoMAFThreshold.hashCode();
    h *= 1000003;
    h ^= (meanPathogenicCADDScore == null) ? 0 : this.meanPathogenicCADDScore.hashCode();
    h *= 1000003;
    h ^= (meanPopulationCADDScore == null) ? 0 : this.meanPopulationCADDScore.hashCode();
    h *= 1000003;
    h ^= (spec95thPerCADDThreshold == null) ? 0 : this.spec95thPerCADDThreshold.hashCode();
    h *= 1000003;
    h ^= (sens95thPerCADDThreshold == null) ? 0 : this.sens95thPerCADDThreshold.hashCode();
    h *= 1000003;
    h ^= this.category.hashCode();
    return h;
  }

}
