
package org.molgenis.core.ui.style;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Style extends Style {

  private final String name;
  private final boolean remote;
  private final String location;

  AutoValue_Style(
      String name,
      boolean remote,
      String location) {
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
    this.remote = remote;
    if (location == null) {
      throw new NullPointerException("Null location");
    }
    this.location = location;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isRemote() {
    return remote;
  }

  @Override
  public String getLocation() {
    return location;
  }

  @Override
  public String toString() {
    return "Style{"
        + "name=" + name + ", "
        + "remote=" + remote + ", "
        + "location=" + location
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Style) {
      Style that = (Style) o;
      return (this.name.equals(that.getName()))
           && (this.remote == that.isRemote())
           && (this.location.equals(that.getLocation()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.name.hashCode();
    h *= 1000003;
    h ^= this.remote ? 1231 : 1237;
    h *= 1000003;
    h ^= this.location.hashCode();
    return h;
  }

}
