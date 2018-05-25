
package org.molgenis.oneclickimporter.model;

import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_DataCollection extends DataCollection {

  private final String name;
  private final List<Column> columns;

  AutoValue_DataCollection(
      String name,
      List<Column> columns) {
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
    if (columns == null) {
      throw new NullPointerException("Null columns");
    }
    this.columns = columns;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<Column> getColumns() {
    return columns;
  }

  @Override
  public String toString() {
    return "DataCollection{"
        + "name=" + name + ", "
        + "columns=" + columns
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof DataCollection) {
      DataCollection that = (DataCollection) o;
      return (this.name.equals(that.getName()))
           && (this.columns.equals(that.getColumns()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.name.hashCode();
    h *= 1000003;
    h ^= this.columns.hashCode();
    return h;
  }

}
