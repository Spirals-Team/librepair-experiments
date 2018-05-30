
package org.molgenis.metadata.manager.model;

import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EditorAttributeResponse extends EditorAttributeResponse {

  private final EditorAttribute attribute;
  private final List<String> languageCodes;

  AutoValue_EditorAttributeResponse(
      EditorAttribute attribute,
      List<String> languageCodes) {
    if (attribute == null) {
      throw new NullPointerException("Null attribute");
    }
    this.attribute = attribute;
    if (languageCodes == null) {
      throw new NullPointerException("Null languageCodes");
    }
    this.languageCodes = languageCodes;
  }

  @Override
  EditorAttribute getAttribute() {
    return attribute;
  }

  @Override
  List<String> getLanguageCodes() {
    return languageCodes;
  }

  @Override
  public String toString() {
    return "EditorAttributeResponse{"
        + "attribute=" + attribute + ", "
        + "languageCodes=" + languageCodes
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EditorAttributeResponse) {
      EditorAttributeResponse that = (EditorAttributeResponse) o;
      return (this.attribute.equals(that.getAttribute()))
           && (this.languageCodes.equals(that.getLanguageCodes()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.attribute.hashCode();
    h *= 1000003;
    h ^= this.languageCodes.hashCode();
    return h;
  }

}
