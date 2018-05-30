
package org.molgenis.semanticmapper.mapping.model;

import java.util.Map;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_CategoryMapping<S, T> extends CategoryMapping<S, T> {

  private final String sourceAttributeName;
  private final Map<S, T> map;
  private final T defaultValue;
  private final boolean defaultValueUndefined;
  private final T nullValue;
  private final boolean nullValueUndefined;

  AutoValue_CategoryMapping(
      String sourceAttributeName,
      Map<S, T> map,
      @Nullable T defaultValue,
      boolean defaultValueUndefined,
      @Nullable T nullValue,
      boolean nullValueUndefined) {
    if (sourceAttributeName == null) {
      throw new NullPointerException("Null sourceAttributeName");
    }
    this.sourceAttributeName = sourceAttributeName;
    if (map == null) {
      throw new NullPointerException("Null map");
    }
    this.map = map;
    this.defaultValue = defaultValue;
    this.defaultValueUndefined = defaultValueUndefined;
    this.nullValue = nullValue;
    this.nullValueUndefined = nullValueUndefined;
  }

  @Override
  public String getSourceAttributeName() {
    return sourceAttributeName;
  }

  @Override
  public Map<S, T> getMap() {
    return map;
  }

  @Nullable
  @Override
  public T getDefaultValue() {
    return defaultValue;
  }

  @Override
  public boolean isDefaultValueUndefined() {
    return defaultValueUndefined;
  }

  @Nullable
  @Override
  public T getNullValue() {
    return nullValue;
  }

  @Override
  public boolean isNullValueUndefined() {
    return nullValueUndefined;
  }

  @Override
  public String toString() {
    return "CategoryMapping{"
        + "sourceAttributeName=" + sourceAttributeName + ", "
        + "map=" + map + ", "
        + "defaultValue=" + defaultValue + ", "
        + "defaultValueUndefined=" + defaultValueUndefined + ", "
        + "nullValue=" + nullValue + ", "
        + "nullValueUndefined=" + nullValueUndefined
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof CategoryMapping) {
      CategoryMapping<?, ?> that = (CategoryMapping<?, ?>) o;
      return (this.sourceAttributeName.equals(that.getSourceAttributeName()))
           && (this.map.equals(that.getMap()))
           && ((this.defaultValue == null) ? (that.getDefaultValue() == null) : this.defaultValue.equals(that.getDefaultValue()))
           && (this.defaultValueUndefined == that.isDefaultValueUndefined())
           && ((this.nullValue == null) ? (that.getNullValue() == null) : this.nullValue.equals(that.getNullValue()))
           && (this.nullValueUndefined == that.isNullValueUndefined());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.sourceAttributeName.hashCode();
    h *= 1000003;
    h ^= this.map.hashCode();
    h *= 1000003;
    h ^= (defaultValue == null) ? 0 : this.defaultValue.hashCode();
    h *= 1000003;
    h ^= this.defaultValueUndefined ? 1231 : 1237;
    h *= 1000003;
    h ^= (nullValue == null) ? 0 : this.nullValue.hashCode();
    h *= 1000003;
    h ^= this.nullValueUndefined ? 1231 : 1237;
    return h;
  }

}
