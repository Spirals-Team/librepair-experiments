
package org.molgenis.data.postgresql.identifier;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_AttributeDescription extends AttributeDescription {

  private final String name;

  AutoValue_AttributeDescription(
      String name) {
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "AttributeDescription{"
        + "name=" + name
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof AttributeDescription) {
      AttributeDescription that = (AttributeDescription) o;
      return (this.name.equals(that.getName()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.name.hashCode();
    return h;
  }

}
