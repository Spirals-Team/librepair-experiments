
package org.molgenis.data.rest.v2;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_CopyEntityRequestV2 extends CopyEntityRequestV2 {

  private final String newEntityName;

  AutoValue_CopyEntityRequestV2(
      String newEntityName) {
    if (newEntityName == null) {
      throw new NullPointerException("Null newEntityName");
    }
    this.newEntityName = newEntityName;
  }

  @NotNull
  @Override
  public String getNewEntityName() {
    return newEntityName;
  }

  @Override
  public String toString() {
    return "CopyEntityRequestV2{"
        + "newEntityName=" + newEntityName
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof CopyEntityRequestV2) {
      CopyEntityRequestV2 that = (CopyEntityRequestV2) o;
      return (this.newEntityName.equals(that.getNewEntityName()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.newEntityName.hashCode();
    return h;
  }

}
