
package org.molgenis.semanticmapper.algorithmgenerator.bean;

import java.util.Set;
import javax.annotation.Generated;
import javax.annotation.Nullable;
import org.molgenis.data.meta.model.Attribute;
import org.molgenis.semanticmapper.mapping.model.AttributeMapping;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_GeneratedAlgorithm extends GeneratedAlgorithm {

  private final String algorithm;
  private final Set<Attribute> sourceAttributes;
  private final AttributeMapping.AlgorithmState algorithmState;

  AutoValue_GeneratedAlgorithm(
      String algorithm,
      @Nullable Set<Attribute> sourceAttributes,
      @Nullable AttributeMapping.AlgorithmState algorithmState) {
    if (algorithm == null) {
      throw new NullPointerException("Null algorithm");
    }
    this.algorithm = algorithm;
    this.sourceAttributes = sourceAttributes;
    this.algorithmState = algorithmState;
  }

  @Override
  public String getAlgorithm() {
    return algorithm;
  }

  @Nullable
  @Override
  public Set<Attribute> getSourceAttributes() {
    return sourceAttributes;
  }

  @Nullable
  @Override
  public AttributeMapping.AlgorithmState getAlgorithmState() {
    return algorithmState;
  }

  @Override
  public String toString() {
    return "GeneratedAlgorithm{"
        + "algorithm=" + algorithm + ", "
        + "sourceAttributes=" + sourceAttributes + ", "
        + "algorithmState=" + algorithmState
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GeneratedAlgorithm) {
      GeneratedAlgorithm that = (GeneratedAlgorithm) o;
      return (this.algorithm.equals(that.getAlgorithm()))
           && ((this.sourceAttributes == null) ? (that.getSourceAttributes() == null) : this.sourceAttributes.equals(that.getSourceAttributes()))
           && ((this.algorithmState == null) ? (that.getAlgorithmState() == null) : this.algorithmState.equals(that.getAlgorithmState()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.algorithm.hashCode();
    h *= 1000003;
    h ^= (sourceAttributes == null) ? 0 : this.sourceAttributes.hashCode();
    h *= 1000003;
    h ^= (algorithmState == null) ? 0 : this.algorithmState.hashCode();
    return h;
  }

}
