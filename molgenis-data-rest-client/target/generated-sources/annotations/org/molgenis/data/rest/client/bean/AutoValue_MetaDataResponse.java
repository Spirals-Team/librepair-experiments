
package org.molgenis.data.rest.client.bean;

import java.util.Map;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_MetaDataResponse extends MetaDataResponse {

  private final String href;
  private final String name;
  private final String label;
  private final Map<String, ? extends MetaDataResponse.Attribute> attributes;
  private final String labelAttribute;

  AutoValue_MetaDataResponse(
      String href,
      String name,
      String label,
      Map<String, ? extends MetaDataResponse.Attribute> attributes,
      String labelAttribute) {
    if (href == null) {
      throw new NullPointerException("Null href");
    }
    this.href = href;
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
    if (label == null) {
      throw new NullPointerException("Null label");
    }
    this.label = label;
    if (attributes == null) {
      throw new NullPointerException("Null attributes");
    }
    this.attributes = attributes;
    if (labelAttribute == null) {
      throw new NullPointerException("Null labelAttribute");
    }
    this.labelAttribute = labelAttribute;
  }

  @Override
  public String getHref() {
    return href;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public Map<String, ? extends MetaDataResponse.Attribute> getAttributes() {
    return attributes;
  }

  @Override
  public String getLabelAttribute() {
    return labelAttribute;
  }

  @Override
  public String toString() {
    return "MetaDataResponse{"
        + "href=" + href + ", "
        + "name=" + name + ", "
        + "label=" + label + ", "
        + "attributes=" + attributes + ", "
        + "labelAttribute=" + labelAttribute
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof MetaDataResponse) {
      MetaDataResponse that = (MetaDataResponse) o;
      return (this.href.equals(that.getHref()))
           && (this.name.equals(that.getName()))
           && (this.label.equals(that.getLabel()))
           && (this.attributes.equals(that.getAttributes()))
           && (this.labelAttribute.equals(that.getLabelAttribute()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.href.hashCode();
    h *= 1000003;
    h ^= this.name.hashCode();
    h *= 1000003;
    h ^= this.label.hashCode();
    h *= 1000003;
    h ^= this.attributes.hashCode();
    h *= 1000003;
    h ^= this.labelAttribute.hashCode();
    return h;
  }

}
