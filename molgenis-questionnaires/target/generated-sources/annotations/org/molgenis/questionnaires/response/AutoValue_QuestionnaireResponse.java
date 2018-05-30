
package org.molgenis.questionnaires.response;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import org.molgenis.questionnaires.meta.QuestionnaireStatus;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_QuestionnaireResponse extends QuestionnaireResponse {

  private final String id;
  private final String label;
  private final String description;
  private final QuestionnaireStatus status;

  AutoValue_QuestionnaireResponse(
      String id,
      String label,
      @Nullable String description,
      QuestionnaireStatus status) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    if (label == null) {
      throw new NullPointerException("Null label");
    }
    this.label = label;
    this.description = description;
    if (status == null) {
      throw new NullPointerException("Null status");
    }
    this.status = status;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getLabel() {
    return label;
  }

  @Nullable
  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public QuestionnaireStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "QuestionnaireResponse{"
        + "id=" + id + ", "
        + "label=" + label + ", "
        + "description=" + description + ", "
        + "status=" + status
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof QuestionnaireResponse) {
      QuestionnaireResponse that = (QuestionnaireResponse) o;
      return (this.id.equals(that.getId()))
           && (this.label.equals(that.getLabel()))
           && ((this.description == null) ? (that.getDescription() == null) : this.description.equals(that.getDescription()))
           && (this.status.equals(that.getStatus()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= this.label.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    h *= 1000003;
    h ^= this.status.hashCode();
    return h;
  }

}
