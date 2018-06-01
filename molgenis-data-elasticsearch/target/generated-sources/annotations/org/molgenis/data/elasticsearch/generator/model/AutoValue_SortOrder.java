
package org.molgenis.data.elasticsearch.generator.model;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_SortOrder extends SortOrder {

  private final String field;
  private final SortDirection direction;

  private AutoValue_SortOrder(
      String field,
      SortDirection direction) {
    this.field = field;
    this.direction = direction;
  }

  @Override
  public String getField() {
    return field;
  }

  @Override
  public SortDirection getDirection() {
    return direction;
  }

  @Override
  public String toString() {
    return "SortOrder{"
        + "field=" + field + ", "
        + "direction=" + direction
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof SortOrder) {
      SortOrder that = (SortOrder) o;
      return (this.field.equals(that.getField()))
           && (this.direction.equals(that.getDirection()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.field.hashCode();
    h *= 1000003;
    h ^= this.direction.hashCode();
    return h;
  }

  static final class Builder extends SortOrder.Builder {
    private String field;
    private SortDirection direction;
    Builder() {
    }
    @Override
    public SortOrder.Builder setField(String field) {
      if (field == null) {
        throw new NullPointerException("Null field");
      }
      this.field = field;
      return this;
    }
    @Override
    public SortOrder.Builder setDirection(SortDirection direction) {
      if (direction == null) {
        throw new NullPointerException("Null direction");
      }
      this.direction = direction;
      return this;
    }
    @Override
    public SortOrder build() {
      String missing = "";
      if (this.field == null) {
        missing += " field";
      }
      if (this.direction == null) {
        missing += " direction";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_SortOrder(
          this.field,
          this.direction);
    }
  }

}
