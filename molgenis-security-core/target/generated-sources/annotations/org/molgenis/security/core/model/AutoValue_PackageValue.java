
package org.molgenis.security.core.model;

import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_PackageValue extends PackageValue {

  private final String name;
  private final String label;
  private final String description;

  private AutoValue_PackageValue(
      String name,
      String label,
      @Nullable String description) {
    this.name = name;
    this.label = label;
    this.description = description;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Nullable
  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "PackageValue{"
        + "name=" + name + ", "
        + "label=" + label + ", "
        + "description=" + description
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof PackageValue) {
      PackageValue that = (PackageValue) o;
      return (this.name.equals(that.getName()))
           && (this.label.equals(that.getLabel()))
           && ((this.description == null) ? (that.getDescription() == null) : this.description.equals(that.getDescription()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.name.hashCode();
    h *= 1000003;
    h ^= this.label.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    return h;
  }

  static final class Builder extends PackageValue.Builder {
    private String name;
    private String label;
    private String description;
    Builder() {
    }
    @Override
    public PackageValue.Builder setName(String name) {
      if (name == null) {
        throw new NullPointerException("Null name");
      }
      this.name = name;
      return this;
    }
    @Override
    public PackageValue.Builder setLabel(String label) {
      if (label == null) {
        throw new NullPointerException("Null label");
      }
      this.label = label;
      return this;
    }
    @Override
    public PackageValue.Builder setDescription(@Nullable String description) {
      this.description = description;
      return this;
    }
    @Override
    public PackageValue build() {
      String missing = "";
      if (this.name == null) {
        missing += " name";
      }
      if (this.label == null) {
        missing += " label";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_PackageValue(
          this.name,
          this.label,
          this.description);
    }
  }

}
