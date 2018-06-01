
package org.molgenis.oneclickimporter.model;

import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Column extends Column {

  private final String name;
  private final int position;
  private final List<Object> dataValues;

  AutoValue_Column(
      String name,
      int position,
      List<Object> dataValues) {
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
    this.position = position;
    if (dataValues == null) {
      throw new NullPointerException("Null dataValues");
    }
    this.dataValues = dataValues;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getPosition() {
    return position;
  }

  @Override
  public List<Object> getDataValues() {
    return dataValues;
  }

  @Override
  public String toString() {
    return "Column{"
        + "name=" + name + ", "
        + "position=" + position + ", "
        + "dataValues=" + dataValues
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Column) {
      Column that = (Column) o;
      return (this.name.equals(that.getName()))
           && (this.position == that.getPosition())
           && (this.dataValues.equals(that.getDataValues()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.name.hashCode();
    h *= 1000003;
    h ^= this.position;
    h *= 1000003;
    h ^= this.dataValues.hashCode();
    return h;
  }

}
