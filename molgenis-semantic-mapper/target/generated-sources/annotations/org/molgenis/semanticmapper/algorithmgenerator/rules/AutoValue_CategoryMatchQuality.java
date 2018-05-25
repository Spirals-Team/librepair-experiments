
package org.molgenis.semanticmapper.algorithmgenerator.rules;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import org.molgenis.semanticmapper.algorithmgenerator.bean.Category;
import org.molgenis.semanticmapper.algorithmgenerator.rules.quality.Quality;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_CategoryMatchQuality<T> extends CategoryMatchQuality<T> {

  private final boolean ruleApplied;
  private final Quality<T> quality;
  private final Category targetCategory;
  private final Category sourceCategory;

  AutoValue_CategoryMatchQuality(
      boolean ruleApplied,
      @Nullable Quality<T> quality,
      Category targetCategory,
      Category sourceCategory) {
    this.ruleApplied = ruleApplied;
    this.quality = quality;
    if (targetCategory == null) {
      throw new NullPointerException("Null targetCategory");
    }
    this.targetCategory = targetCategory;
    if (sourceCategory == null) {
      throw new NullPointerException("Null sourceCategory");
    }
    this.sourceCategory = sourceCategory;
  }

  @Override
  public boolean isRuleApplied() {
    return ruleApplied;
  }

  @Nullable
  @Override
  public Quality<T> getQuality() {
    return quality;
  }

  @Override
  public Category getTargetCategory() {
    return targetCategory;
  }

  @Override
  public Category getSourceCategory() {
    return sourceCategory;
  }

  @Override
  public String toString() {
    return "CategoryMatchQuality{"
        + "ruleApplied=" + ruleApplied + ", "
        + "quality=" + quality + ", "
        + "targetCategory=" + targetCategory + ", "
        + "sourceCategory=" + sourceCategory
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof CategoryMatchQuality) {
      CategoryMatchQuality<?> that = (CategoryMatchQuality<?>) o;
      return (this.ruleApplied == that.isRuleApplied())
           && ((this.quality == null) ? (that.getQuality() == null) : this.quality.equals(that.getQuality()))
           && (this.targetCategory.equals(that.getTargetCategory()))
           && (this.sourceCategory.equals(that.getSourceCategory()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.ruleApplied ? 1231 : 1237;
    h *= 1000003;
    h ^= (quality == null) ? 0 : this.quality.hashCode();
    h *= 1000003;
    h ^= this.targetCategory.hashCode();
    h *= 1000003;
    h ^= this.sourceCategory.hashCode();
    return h;
  }

}
