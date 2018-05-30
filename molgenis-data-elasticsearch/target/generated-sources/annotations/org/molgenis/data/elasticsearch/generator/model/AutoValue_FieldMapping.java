
package org.molgenis.data.elasticsearch.generator.model;

import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_FieldMapping extends FieldMapping {

  private final String name;
  private final MappingType type;
  private final List<FieldMapping> nestedFieldMappings;

  private AutoValue_FieldMapping(
      String name,
      MappingType type,
      @Nullable List<FieldMapping> nestedFieldMappings) {
    this.name = name;
    this.type = type;
    this.nestedFieldMappings = nestedFieldMappings;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public MappingType getType() {
    return type;
  }

  @Nullable
  @Override
  public List<FieldMapping> getNestedFieldMappings() {
    return nestedFieldMappings;
  }

  @Override
  public String toString() {
    return "FieldMapping{"
        + "name=" + name + ", "
        + "type=" + type + ", "
        + "nestedFieldMappings=" + nestedFieldMappings
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof FieldMapping) {
      FieldMapping that = (FieldMapping) o;
      return (this.name.equals(that.getName()))
           && (this.type.equals(that.getType()))
           && ((this.nestedFieldMappings == null) ? (that.getNestedFieldMappings() == null) : this.nestedFieldMappings.equals(that.getNestedFieldMappings()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.name.hashCode();
    h *= 1000003;
    h ^= this.type.hashCode();
    h *= 1000003;
    h ^= (nestedFieldMappings == null) ? 0 : this.nestedFieldMappings.hashCode();
    return h;
  }

  static final class Builder extends FieldMapping.Builder {
    private String name;
    private MappingType type;
    private List<FieldMapping> nestedFieldMappings;
    Builder() {
    }
    @Override
    public FieldMapping.Builder setName(String name) {
      if (name == null) {
        throw new NullPointerException("Null name");
      }
      this.name = name;
      return this;
    }
    @Override
    public FieldMapping.Builder setType(MappingType type) {
      if (type == null) {
        throw new NullPointerException("Null type");
      }
      this.type = type;
      return this;
    }
    @Override
    public FieldMapping.Builder setNestedFieldMappings(@Nullable List<FieldMapping> nestedFieldMappings) {
      this.nestedFieldMappings = nestedFieldMappings;
      return this;
    }
    @Override
    public FieldMapping build() {
      String missing = "";
      if (this.name == null) {
        missing += " name";
      }
      if (this.type == null) {
        missing += " type";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_FieldMapping(
          this.name,
          this.type,
          this.nestedFieldMappings);
    }
  }

}
