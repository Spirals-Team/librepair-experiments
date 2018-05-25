
package org.molgenis.data.rest.client.bean;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_MetaDataResponse_Attribute extends MetaDataResponse.Attribute {

  private final String href;

  AutoValue_MetaDataResponse_Attribute(
      String href) {
    if (href == null) {
      throw new NullPointerException("Null href");
    }
    this.href = href;
  }

  @Override
  public String getHref() {
    return href;
  }

  @Override
  public String toString() {
    return "Attribute{"
        + "href=" + href
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof MetaDataResponse.Attribute) {
      MetaDataResponse.Attribute that = (MetaDataResponse.Attribute) o;
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
