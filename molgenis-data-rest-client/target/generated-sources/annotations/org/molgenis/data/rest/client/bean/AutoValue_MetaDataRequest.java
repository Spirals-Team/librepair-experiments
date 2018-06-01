
package org.molgenis.data.rest.client.bean;

import java.util.Collection;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_MetaDataRequest extends MetaDataRequest {

  private final Collection<String> attributes;
  private final Collection<String> expands;

  AutoValue_MetaDataRequest(
      @Nullable Collection<String> attributes,
      @Nullable Collection<String> expands) {
    this.attributes = attributes;
    this.expands = expands;
  }

  @Nullable
  @Override
  public Collection<String> getAttributes() {
    return attributes;
  }

  @Nullable
  @Override
  public Collection<String> getExpands() {
    return expands;
  }

  @Override
  public String toString() {
    return "MetaDataRequest{"
        + "attributes=" + attributes + ", "
        + "expands=" + expands
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof MetaDataRequest) {
      MetaDataRequest that = (MetaDataRequest) o;
      return ((this.attributes == null) ? (that.getAttributes() == null) : this.attributes.equals(that.getAttributes()))
           && ((this.expands == null) ? (that.getExpands() == null) : this.expands.equals(that.getExpands()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (attributes == null) ? 0 : this.attributes.hashCode();
    h *= 1000003;
    h ^= (expands == null) ? 0 : this.expands.hashCode();
    return h;
  }

}
