
package org.molgenis.semanticsearch.explain.bean;

import java.util.Map;
import java.util.Set;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_ExplainedAttribute extends ExplainedAttribute {

  private final Map<String, Object> attribute;
  private final Set<ExplainedQueryString> explainedQueryStrings;
  private final boolean highQuality;

  AutoValue_ExplainedAttribute(
      Map<String, Object> attribute,
      Set<ExplainedQueryString> explainedQueryStrings,
      boolean highQuality) {
    if (attribute == null) {
      throw new NullPointerException("Null attribute");
    }
    this.attribute = attribute;
    if (explainedQueryStrings == null) {
      throw new NullPointerException("Null explainedQueryStrings");
    }
    this.explainedQueryStrings = explainedQueryStrings;
    this.highQuality = highQuality;
  }

  @Override
  public Map<String, Object> getAttribute() {
    return attribute;
  }

  @Override
  public Set<ExplainedQueryString> getExplainedQueryStrings() {
    return explainedQueryStrings;
  }

  @Override
  public boolean isHighQuality() {
    return highQuality;
  }

  @Override
  public String toString() {
    return "ExplainedAttribute{"
        + "attribute=" + attribute + ", "
        + "explainedQueryStrings=" + explainedQueryStrings + ", "
        + "highQuality=" + highQuality
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof ExplainedAttribute) {
      ExplainedAttribute that = (ExplainedAttribute) o;
      return (this.attribute.equals(that.getAttribute()))
           && (this.explainedQueryStrings.equals(that.getExplainedQueryStrings()))
           && (this.highQuality == that.isHighQuality());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.attribute.hashCode();
    h *= 1000003;
    h ^= this.explainedQueryStrings.hashCode();
    h *= 1000003;
    h ^= this.highQuality ? 1231 : 1237;
    return h;
  }

}
