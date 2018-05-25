
package org.molgenis.metadata.manager.model;

import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_EditorEntityType extends EditorEntityType {

  private final String id;
  private final String label;
  private final Map<String, String> labelI18n;
  private final String description;
  private final Map<String, String> descriptionI18n;
  private final boolean abstract0;
  private final String backend;
  private final EditorPackageIdentifier package0;
  private final EditorEntityTypeParent entityTypeParent;
  private final List<EditorAttribute> attributes;
  private final List<EditorAttributeIdentifier> referringAttributes;
  private final List<EditorTagIdentifier> tags;
  private final EditorAttributeIdentifier idAttribute;
  private final EditorAttributeIdentifier labelAttribute;
  private final List<EditorAttributeIdentifier> lookupAttributes;

  AutoValue_EditorEntityType(
      String id,
      @Nullable String label,
      Map<String, String> labelI18n,
      @Nullable String description,
      Map<String, String> descriptionI18n,
      boolean abstract0,
      String backend,
      @Nullable EditorPackageIdentifier package0,
      @Nullable EditorEntityTypeParent entityTypeParent,
      List<EditorAttribute> attributes,
      List<EditorAttributeIdentifier> referringAttributes,
      List<EditorTagIdentifier> tags,
      @Nullable EditorAttributeIdentifier idAttribute,
      @Nullable EditorAttributeIdentifier labelAttribute,
      List<EditorAttributeIdentifier> lookupAttributes) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    this.label = label;
    if (labelI18n == null) {
      throw new NullPointerException("Null labelI18n");
    }
    this.labelI18n = labelI18n;
    this.description = description;
    if (descriptionI18n == null) {
      throw new NullPointerException("Null descriptionI18n");
    }
    this.descriptionI18n = descriptionI18n;
    this.abstract0 = abstract0;
    if (backend == null) {
      throw new NullPointerException("Null backend");
    }
    this.backend = backend;
    this.package0 = package0;
    this.entityTypeParent = entityTypeParent;
    if (attributes == null) {
      throw new NullPointerException("Null attributes");
    }
    this.attributes = attributes;
    if (referringAttributes == null) {
      throw new NullPointerException("Null referringAttributes");
    }
    this.referringAttributes = referringAttributes;
    if (tags == null) {
      throw new NullPointerException("Null tags");
    }
    this.tags = tags;
    this.idAttribute = idAttribute;
    this.labelAttribute = labelAttribute;
    if (lookupAttributes == null) {
      throw new NullPointerException("Null lookupAttributes");
    }
    this.lookupAttributes = lookupAttributes;
  }

  @Override
  public String getId() {
    return id;
  }

  @Nullable
  @Override
  public String getLabel() {
    return label;
  }

  @Override
  public Map<String, String> getLabelI18n() {
    return labelI18n;
  }

  @Nullable
  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public Map<String, String> getDescriptionI18n() {
    return descriptionI18n;
  }

  @Override
  public boolean isAbstract() {
    return abstract0;
  }

  @Override
  public String getBackend() {
    return backend;
  }

  @Nullable
  @Override
  public EditorPackageIdentifier getPackage() {
    return package0;
  }

  @Nullable
  @Override
  public EditorEntityTypeParent getEntityTypeParent() {
    return entityTypeParent;
  }

  @Override
  public List<EditorAttribute> getAttributes() {
    return attributes;
  }

  @Override
  public List<EditorAttributeIdentifier> getReferringAttributes() {
    return referringAttributes;
  }

  @Override
  public List<EditorTagIdentifier> getTags() {
    return tags;
  }

  @Nullable
  @Override
  public EditorAttributeIdentifier getIdAttribute() {
    return idAttribute;
  }

  @Nullable
  @Override
  public EditorAttributeIdentifier getLabelAttribute() {
    return labelAttribute;
  }

  @Override
  public List<EditorAttributeIdentifier> getLookupAttributes() {
    return lookupAttributes;
  }

  @Override
  public String toString() {
    return "EditorEntityType{"
        + "id=" + id + ", "
        + "label=" + label + ", "
        + "labelI18n=" + labelI18n + ", "
        + "description=" + description + ", "
        + "descriptionI18n=" + descriptionI18n + ", "
        + "abstract=" + abstract0 + ", "
        + "backend=" + backend + ", "
        + "package=" + package0 + ", "
        + "entityTypeParent=" + entityTypeParent + ", "
        + "attributes=" + attributes + ", "
        + "referringAttributes=" + referringAttributes + ", "
        + "tags=" + tags + ", "
        + "idAttribute=" + idAttribute + ", "
        + "labelAttribute=" + labelAttribute + ", "
        + "lookupAttributes=" + lookupAttributes
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof EditorEntityType) {
      EditorEntityType that = (EditorEntityType) o;
      return (this.id.equals(that.getId()))
           && ((this.label == null) ? (that.getLabel() == null) : this.label.equals(that.getLabel()))
           && (this.labelI18n.equals(that.getLabelI18n()))
           && ((this.description == null) ? (that.getDescription() == null) : this.description.equals(that.getDescription()))
           && (this.descriptionI18n.equals(that.getDescriptionI18n()))
           && (this.abstract0 == that.isAbstract())
           && (this.backend.equals(that.getBackend()))
           && ((this.package0 == null) ? (that.getPackage() == null) : this.package0.equals(that.getPackage()))
           && ((this.entityTypeParent == null) ? (that.getEntityTypeParent() == null) : this.entityTypeParent.equals(that.getEntityTypeParent()))
           && (this.attributes.equals(that.getAttributes()))
           && (this.referringAttributes.equals(that.getReferringAttributes()))
           && (this.tags.equals(that.getTags()))
           && ((this.idAttribute == null) ? (that.getIdAttribute() == null) : this.idAttribute.equals(that.getIdAttribute()))
           && ((this.labelAttribute == null) ? (that.getLabelAttribute() == null) : this.labelAttribute.equals(that.getLabelAttribute()))
           && (this.lookupAttributes.equals(that.getLookupAttributes()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.id.hashCode();
    h *= 1000003;
    h ^= (label == null) ? 0 : this.label.hashCode();
    h *= 1000003;
    h ^= this.labelI18n.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    h *= 1000003;
    h ^= this.descriptionI18n.hashCode();
    h *= 1000003;
    h ^= this.abstract0 ? 1231 : 1237;
    h *= 1000003;
    h ^= this.backend.hashCode();
    h *= 1000003;
    h ^= (package0 == null) ? 0 : this.package0.hashCode();
    h *= 1000003;
    h ^= (entityTypeParent == null) ? 0 : this.entityTypeParent.hashCode();
    h *= 1000003;
    h ^= this.attributes.hashCode();
    h *= 1000003;
    h ^= this.referringAttributes.hashCode();
    h *= 1000003;
    h ^= this.tags.hashCode();
    h *= 1000003;
    h ^= (idAttribute == null) ? 0 : this.idAttribute.hashCode();
    h *= 1000003;
    h ^= (labelAttribute == null) ? 0 : this.labelAttribute.hashCode();
    h *= 1000003;
    h ^= this.lookupAttributes.hashCode();
    return h;
  }

}
