
package org.molgenis.data.elasticsearch.generator.model;

import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Mapping extends Mapping {

  private final String type;
  private final List<FieldMapping> fieldMappings;

  private AutoValue_Mapping(
      String type,
      List<FieldMapping> fieldMappings) {
    this.type = type;
    this.fieldMappings = fieldMappings;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public List<FieldMapping> getFieldMappings() {
    return fieldMappings;
  }

  @Override
  public String toString() {
    return "Mapping{"
        + "type=" + type + ", "
        + "fieldMappings=" + fieldMappings
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Mapping) {
      Mapping that = (Mapping) o;
      return (this.type.equals(that.getType()))
           && (this.fieldMappings.equals(that.getFieldMappings()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.type.hashCode();
    h *= 1000003;
    h ^= this.fieldMappings.hashCode();
    return h;
  }

  static final class Builder extends Mapping.Builder {
    private String type;
    private List<FieldMapping> fieldMappings;
    Builder() {
    }
    @Override
    public Mapping.Builder setType(String type) {
      if (type == null) {
        throw new NullPointerException("Null type");
      }
      this.type = type;
      return this;
    }
    @Override
    public Mapping.Builder setFieldMappings(List<FieldMapping> fieldMappings) {
      if (fieldMappings == null) {
        throw new NullPointerException("Null fieldMappings");
      }
      this.fieldMappings = fieldMappings;
      return this;
    }
    @Override
    public Mapping build() {
      String missing = "";
      if (this.type == null) {
        missing += " type";
      }
      if (this.fieldMappings == null) {
        missing += " fieldMappings";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_Mapping(
          this.type,
          this.fieldMappings);
    }
  }

}
