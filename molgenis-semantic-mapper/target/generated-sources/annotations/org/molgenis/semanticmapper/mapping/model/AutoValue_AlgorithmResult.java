
package org.molgenis.semanticmapper.mapping.model;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import org.molgenis.data.Entity;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_AlgorithmResult extends AlgorithmResult {

  private final Object value;
  private final Exception exception;
  private final Entity sourceEntity;

  AutoValue_AlgorithmResult(
      @Nullable Object value,
      @Nullable Exception exception,
      Entity sourceEntity) {
    this.value = value;
    this.exception = exception;
    if (sourceEntity == null) {
      throw new NullPointerException("Null sourceEntity");
    }
    this.sourceEntity = sourceEntity;
  }

  @Nullable
  @Override
  public Object getValue() {
    return value;
  }

  @Nullable
  @Override
  public Exception getException() {
    return exception;
  }

  @Override
  public Entity getSourceEntity() {
    return sourceEntity;
  }

  @Override
  public String toString() {
    return "AlgorithmResult{"
        + "value=" + value + ", "
        + "exception=" + exception + ", "
        + "sourceEntity=" + sourceEntity
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof AlgorithmResult) {
      AlgorithmResult that = (AlgorithmResult) o;
      return ((this.value == null) ? (that.getValue() == null) : this.value.equals(that.getValue()))
           && ((this.exception == null) ? (that.getException() == null) : this.exception.equals(that.getException()))
           && (this.sourceEntity.equals(that.getSourceEntity()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (value == null) ? 0 : this.value.hashCode();
    h *= 1000003;
    h ^= (exception == null) ? 0 : this.exception.hashCode();
    h *= 1000003;
    h ^= this.sourceEntity.hashCode();
    return h;
  }

}
