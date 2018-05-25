
package org.molgenis.metadata.manager.model;

import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EditorEntityTypeResponse extends EditorEntityTypeResponse {

  private final EditorEntityType entityType;
  private final List<String> languageCodes;

  AutoValue_EditorEntityTypeResponse(
      EditorEntityType entityType,
      List<String> languageCodes) {
    if (entityType == null) {
      throw new NullPointerException("Null entityType");
    }
    this.entityType = entityType;
    if (languageCodes == null) {
      throw new NullPointerException("Null languageCodes");
    }
    this.languageCodes = languageCodes;
  }

  @Override
  EditorEntityType getEntityType() {
    return entityType;
  }

  @Override
  List<String> getLanguageCodes() {
    return languageCodes;
  }

  @Override
  public String toString() {
    return "EditorEntityTypeResponse{"
        + "entityType=" + entityType + ", "
        + "languageCodes=" + languageCodes
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EditorEntityTypeResponse) {
      EditorEntityTypeResponse that = (EditorEntityTypeResponse) o;
      return (this.entityType.equals(that.getEntityType()))
           && (this.languageCodes.equals(that.getLanguageCodes()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.entityType.hashCode();
    h *= 1000003;
    h ^= this.languageCodes.hashCode();
    return h;
  }

}
