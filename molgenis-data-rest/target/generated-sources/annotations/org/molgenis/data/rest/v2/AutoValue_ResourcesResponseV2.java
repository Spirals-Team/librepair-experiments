
package org.molgenis.data.rest.v2;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_ResourcesResponseV2 extends ResourcesResponseV2 {

  private final String href;

  AutoValue_ResourcesResponseV2(
      String href) {
    if (href == null) {
      throw new NullPointerException("Null href");
    }
    this.href = href;
  }

  @NotNull
  @NotNull
  @Override
  public String getHref() {
    return href;
  }

  @Override
  public String toString() {
    return "ResourcesResponseV2{"
        + "href=" + href
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof ResourcesResponseV2) {
      ResourcesResponseV2 that = (ResourcesResponseV2) o;
      return (this.href.equals(that.getHref()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.href.hashCode();
    return h;
  }

}
